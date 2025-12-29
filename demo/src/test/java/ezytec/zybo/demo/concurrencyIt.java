package ezytec.zybo.demo;


import ezytec.zybo.demo.domain.Stay;
import ezytec.zybo.demo.domain.StayStatus;
import ezytec.zybo.demo.domain.User;
import ezytec.zybo.demo.domain.Vehicle;
import ezytec.zybo.demo.repository.StayRepository;
import ezytec.zybo.demo.repository.UserRepository;
import ezytec.zybo.demo.repository.VehicleRepository;
import ezytec.zybo.demo.service.StayService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@org.springframework.test.context.ActiveProfiles("test")
public class concurrencyIt {

    @Autowired
    UserRepository userRepo;
    @Autowired
    VehicleRepository vehicleRepo;
    @Autowired
    StayRepository stayRepo;
    @Autowired
    StayService stayService;


    @Test
    void concurrentEntry_sameVehicle_onlyOneOpenStay() throws Exception {
        User u = User.builder().names("David").document("DOC-1").phone("3000000000").build();
        u = userRepo.save(u);

        Vehicle v = new Vehicle();
        v.setPlate("AAA111");
        v.setUser(u);
        v = vehicleRepo.save(v);

        int threads = 2;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger conflicts = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            Vehicle finalV = v;
            pool.submit(() -> {
                ready.countDown();
                start.await();
                try {
                    stayService.registerEntry(finalV.getId());
                    success.incrementAndGet();
                } catch (RuntimeException ex) {
                    conflicts.incrementAndGet();
                }
                return null;
            });
        }

        ready.await();
        start.countDown();

        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));

        assertEquals(1, success.get());
        assertEquals(1, conflicts.get());

        Vehicle finalV1 = v;
        long openCount = stayRepo.findAll().stream()
                .filter(s -> s.getVehicle().getId().equals(finalV1.getId()))
                .filter(s -> s.getStatus() == StayStatus.OPEN)
                .count();

        assertEquals(1, openCount);
    }

    @Test
    void concurrentExit_sameStay_onlyOneCloses() throws Exception {
        // Setup minimal: user+vehicle+stay open
        User u = User.builder().names("Ana").document("DOC-2").phone("3111111111").build();
        u = userRepo.save(u);

        Vehicle v = new Vehicle();
        v.setPlate("BBB222");
        v.setUser(u);
        v = vehicleRepo.save(v);

        Stay stay = stayService.registerEntry(v.getId());

        int threads = 2;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger conflicts = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                ready.countDown();
                start.await();
                try {
                    stayService.registerExit(stay.getId());
                    success.incrementAndGet();
                } catch (RuntimeException ex) {
                    conflicts.incrementAndGet();
                }
                return null;
            });
        }

        ready.await();
        start.countDown();

        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));

        assertEquals(1, success.get());
        assertEquals(1, conflicts.get());

        Stay reloaded = stayRepo.findById(stay.getId()).orElseThrow();
        assertEquals(StayStatus.CLOSED, reloaded.getStatus());
        assertNotNull(reloaded.getExitTime());
        assertNotNull(reloaded.getChargedValue());
    }
}
