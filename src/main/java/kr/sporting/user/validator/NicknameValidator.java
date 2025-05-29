package kr.sporting.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.sporting.user.annotation.Nickname;
import kr.sporting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{3,12}$");

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!NICKNAME_PATTERN.matcher(value).matches()) {
            return false;
        }

        if (userService.isNicknameAlreadyExists(value)) {
            return false;
        }

        return true;
    }
}
