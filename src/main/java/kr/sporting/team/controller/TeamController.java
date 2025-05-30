package kr.sporting.team.controller;

import jakarta.validation.Valid;
import kr.sporting.team.domain.Team;
import kr.sporting.team.dto.CreateTeamRequest;
import kr.sporting.team.dto.UpdateTeamRequest;
import kr.sporting.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/teams")
    public ResponseEntity<Team> getTeams() {
        teamService.getTeams();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable Long id) {
        try {
            Team team = teamService.getTeamById(id);
            return ResponseEntity.ok(team);
        } catch (Throwable e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody @Valid CreateTeamRequest request) {
        teamService.createTeam(request.toEntity());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody @Valid UpdateTeamRequest request) {
        teamService.updateTeam(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
    
}

