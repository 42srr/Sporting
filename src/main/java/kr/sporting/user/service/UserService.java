package kr.sporting.user.service;

import kr.sporting.exception.UserNotFoundException;
import kr.sporting.user.domain.User;
import kr.sporting.user.dto.request.CreateUserRequest;
import kr.sporting.user.dto.request.UpdateUserRequest;
import kr.sporting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User createUser(CreateUserRequest request) {
        final String encodedPassword = bCryptPasswordEncoder.encode(request.password());

        final User entity = User.builder()
                .nickname(request.nickname())
                .password(encodedPassword)
                .email(request.email())
                .birthDate(request.birthDate())
                .build();

        return userRepository.save(entity);
    }

    @Transactional
    public User updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (request.nickname() != null) {
            user.setNickname(request.nickname());
        }

        if (request.address() != null) {
            user.setAddress(request.address());
        }

        if (request.birthDate() != null) {
            user.setBirthDate(request.birthDate());
        }

        return user;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameAlreadyExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
