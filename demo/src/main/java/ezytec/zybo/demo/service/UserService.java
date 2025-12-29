package ezytec.zybo.demo.service;

import ezytec.zybo.demo.domain.User;
import ezytec.zybo.demo.exception.CustomExceptions;
import ezytec.zybo.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;

    @Transactional
    public User create(User u) {
        try {
            return repo.save(u);
        } catch (DataIntegrityViolationException e) {
            throw new CustomExceptions.ConflictException("Document or phone already exists");
        }
    }

    public User get(Long id) {
        return repo.findById(id).orElseThrow(() -> new CustomExceptions.NotFoundException("User not found"));
    }

    @Transactional
    public User update(Long id, User patch) {
        User current = get(id);
        current.setNames(patch.getNames());
        current.setPhone(patch.getPhone());
        try {
            return repo.save(current);
        } catch (DataIntegrityViolationException e) {
            throw new CustomExceptions.ConflictException("Phone already exists");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new CustomExceptions.NotFoundException("User not found");
        repo.deleteById(id);
    }
}

