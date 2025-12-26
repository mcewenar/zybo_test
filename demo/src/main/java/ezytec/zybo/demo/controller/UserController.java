package ezytec.zybo.demo.controller;


import ezytec.zybo.demo.dto.UserCreateRequest;
import ezytec.zybo.demo.dto.UserResponse;
import ezytec.zybo.demo.dto.UserUpdateRequest;
import ezytec.zybo.demo.mappers.UserMapper;
import ezytec.zybo.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest req) {
        return ResponseEntity.ok(UserMapper.toResponse(userService.create(UserMapper.toEntity(req))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.toResponse(userService.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(UserMapper.toResponse(userService.update(id, UserMapper.toEntityForUpdate(id,req))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
