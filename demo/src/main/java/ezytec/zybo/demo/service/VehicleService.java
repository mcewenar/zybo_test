package ezytec.zybo.demo.service;

import ezytec.zybo.demo.domain.User;
import ezytec.zybo.demo.domain.Vehicle;
import ezytec.zybo.demo.exception.CustomExceptions;
import ezytec.zybo.demo.repository.UserRepository;
import ezytec.zybo.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepo;
    private final UserRepository userRepo;

    @Transactional
    public Vehicle create(String plate, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new CustomExceptions.NotFoundException("User not found"));
        Vehicle v = new Vehicle();
        v.setPlate(plate);
        v.setUser(user);

        try {
            return vehicleRepo.save(v);
        } catch (DataIntegrityViolationException e) {
            throw new CustomExceptions.ConflictException("Plate already exists");
        }
    }

    public Vehicle get(Long id) {
        return vehicleRepo.findById(id).orElseThrow(() -> new CustomExceptions.NotFoundException("Vehicle not found"));
    }

    @Transactional
    public Vehicle update(Long id, String plate, Long userId) {
        Vehicle current = get(id);
        User user = userRepo.findById(userId).orElseThrow(() -> new CustomExceptions.NotFoundException("User not found"));
        current.setPlate(plate);
        current.setUser(user);
        try {
            return vehicleRepo.save(current);
        } catch (DataIntegrityViolationException e) {
            throw new CustomExceptions.ConflictException("Plate already exists");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!vehicleRepo.existsById(id))
            throw new CustomExceptions.NotFoundException("Vehicle not found");
        vehicleRepo.deleteById(id);
    }
}
