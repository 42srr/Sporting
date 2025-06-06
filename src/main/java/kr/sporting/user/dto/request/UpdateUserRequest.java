package kr.sporting.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.sporting.user.annotation.Nickname;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

@Schema(description = "유저정보 수정 요청")
public record UpdateUserRequest(
        @Nullable
        @Nickname
        @Schema(description = "유저 닉네임", nullable = true)
        String nickname,

        @Nullable
        @Schema(description = "유저 주소", nullable = true)
        String address,

        @Nullable
        @Schema(description = "유저 생일", nullable = true)
        LocalDate birthDate
) {
}
