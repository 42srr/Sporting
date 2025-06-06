package kr.sporting.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "팀 응답")
public class TeamResponse {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "최대 인원 수")
    private Integer size;
}
