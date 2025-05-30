package kr.sporting.team.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.sporting.team.domain.Team;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Schema(description = "팀 생성")
public class CreateTeamRequest {

    @NotBlank(message = "팀 이름은 필수 입력값입니다.")
    @Schema(description = "팀 이름")
    private String teamName;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    @Schema(description = "팀 지역")
    private String address;

    @NotNull(message = "최대 멤버 수는 필수 입력값입니다.")
    @Min(value = 2, message = "최대 팀원 수는 1보다 커야합니다.")
    @Schema(description = "팀 최대 인원")
    private Integer teamSize;

    public Team toEntity() {
        return Team.builder()
                .teamName(this.teamName)
                .address(this.address)
                .teamSize(this.teamSize)
                .build();
    }
}
