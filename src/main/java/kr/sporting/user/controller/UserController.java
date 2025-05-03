package kr.sporting.user.controller;

import kr.sporting.user.api.UserApi;
import kr.sporting.user.domain.User;
import kr.sporting.user.dto.request.CreateUserRequest;
import kr.sporting.user.dto.request.UpdateUserRequest;
import kr.sporting.user.dto.response.UserResponse;
import kr.sporting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserResponse getUser(Long userId) {
        return toUserResponse(userService.getUserById(userId));
    }

    @Override
    public UserResponse signUp(CreateUserRequest request) {
        return toUserResponse(userService.createUser(request));
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        return toUserResponse(userService.updateUser(userId, request));
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDate(),
                user.getCreatedAt()
        );
    }
}
