package ezytec.zybo.demo.mappers;

import ezytec.zybo.demo.domain.User;
import ezytec.zybo.demo.dto.UserCreateRequest;
import ezytec.zybo.demo.dto.UserResponse;
import ezytec.zybo.demo.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public final class UserMapper {



    public static User toEntity(UserCreateRequest request) {
        return User.builder()
                .names(request.names())
                .document(request.document())
                .phone(request.phone())
                .build();
    }

    public static User toEntityForUpdate(Long id, UserUpdateRequest req) {
        return User.builder()
                .id(id)
                .names(req.names())
                .phone(req.phone())
                .build();
    }



    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .names(user.getNames())
                .document(user.getDocument())
                .phone(user.getPhone())
                .build();
    }
}
