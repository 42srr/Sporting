package kr.sporting.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.sporting.team.domain.Team;
import kr.sporting.team.dto.CreateTeamRequest;
import kr.sporting.team.dto.TeamResponse;
import kr.sporting.team.dto.UpdateTeamRequest;
import kr.sporting.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Tag(name = "Team API")
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "전체 팀 조회")
    @GetMapping
    public ResponseEntity<Stream<TeamResponse>> getTeams() {
        ArrayList<Team> teamStream = (ArrayList<Team>) teamService.getTeams();
        Stream<TeamResponse> teamResponses = teamStream.stream().map(this::toTeamResponse);
        return ResponseEntity.ok(teamResponses);
    }

    @Operation(summary = "팀 조회")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable Long id) {
        try {
            Team team = teamService.getTeamById(id);
            return ResponseEntity.ok(toTeamResponse(team));
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "팀 생성")
    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody @Valid CreateTeamRequest request) {
        Team team = teamService.createTeam(request.toEntity());
        return ResponseEntity.ok(toTeamResponse(team));
    }

    @Operation(summary = "팀 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id, @RequestBody @Valid UpdateTeamRequest request) {
        Team team = teamService.updateTeam(id, request);
        return ResponseEntity.ok(toTeamResponse(team));
    }

    @Operation(summary = "팀 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamResponse> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }

    private TeamResponse toTeamResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getAddress(),
                team.getSize()
        );
    }
}

