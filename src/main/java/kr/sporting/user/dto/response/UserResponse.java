package kr.sporting.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "유저 응답")
public record UserResponse(
        @Schema(description = "유저 ID")
        Long userId,

        @Schema(description = "유저 닉네임")
        String nickname,

        @Schema(description = "유저 이메일")
        String email,

        @Nullable
        @Schema(description = "유저 주소", nullable = true)
        String address,

        @Nullable
        @Schema(description = "유저 생일", nullable = true)
        LocalDate birthDate,

        @Schema(description = "유저 생성일시")
        LocalDateTime createdAt
) {
}
