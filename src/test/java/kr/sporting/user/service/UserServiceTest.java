package kr.sporting.user.service;

import kr.sporting.exception.UserNotFoundException;
import kr.sporting.user.domain.User;
import kr.sporting.user.dto.request.CreateUserRequest;
import kr.sporting.user.dto.request.UpdateUserRequest;
import kr.sporting.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser_성공() {
        // given
        CreateUserRequest request = new CreateUserRequest(
                "newNick", "plainPw", "test@email.com", LocalDate.of(1990, 1, 1)
        );
        String encodedPw = "encodedPw";
        User savedUser = User.builder()
                .nickname("newNick")
                .password(encodedPw)
                .email("test@email.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        when(bCryptPasswordEncoder.encode(request.password())).thenReturn(encodedPw);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        User result = userService.createUser(request);

        // then
        assertThat(result.getNickname()).isEqualTo("newNick");
        assertThat(result.getPassword()).isEqualTo(encodedPw);
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        assertThat(result.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
        verify(bCryptPasswordEncoder).encode("plainPw");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_성공() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .nickname("oldNick")
                .address("oldAddress")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        UpdateUserRequest request = new UpdateUserRequest(
                "newNick", "newAddress", LocalDate.of(2000, 1, 1)
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User updated = userService.updateUser(userId, request);

        // then
        assertThat(updated.getNickname()).isEqualTo("newNick");
        assertThat(updated.getAddress()).isEqualTo("newAddress");
        assertThat(updated.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_없는유저_예외() {
        // given
        Long userId = 99L;
        UpdateUserRequest request = new UpdateUserRequest("nick", null, null);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.updateUser(userId, request))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_성공() {
        // given
        Long id = 1L;
        User user = User.builder().id(id).nickname("nick").build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when
        User result = userService.getUserById(id);

        // then
        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_없는유저_예외() {
        // given
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository).findById(id);
    }
}
