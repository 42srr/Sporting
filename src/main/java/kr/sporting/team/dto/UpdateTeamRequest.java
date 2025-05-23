package kr.sporting.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Schema(description = "팀 수정")
public class UpdateTeamRequest {

    @NotBlank(message = "팀 이름은 필수 입력값입니다.")
    @Schema(description = "팀 이름", nullable = true)
    private String teamName;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    @Schema(description = "팀 주소", nullable = true)
    private String address;

    @NotNull(message = "최대 멤버 수는 필수 입력값입니다.")
    @Min(value = 2, message = "최대 팀원 수는 1보다 커야합니다.")
    @Schema(description = "팀 최대 인원", nullable = true)
    private Integer teamSize;
}
