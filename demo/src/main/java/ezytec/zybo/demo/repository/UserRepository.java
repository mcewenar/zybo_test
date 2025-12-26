package ezytec.zybo.demo.repository;

import ezytec.zybo.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
