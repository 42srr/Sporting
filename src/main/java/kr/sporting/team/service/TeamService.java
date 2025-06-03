package kr.sporting.team.service;

import kr.sporting.team.domain.Team;
import kr.sporting.team.dto.UpdateTeamRequest;
import kr.sporting.team.exception.TeamNotFoundException;
import kr.sporting.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public Team createTeam(Team team) {
        teamRepository.save(team);
        return (team);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Transactional
    public Team updateTeam(Long teamId, UpdateTeamRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        if (request.getName() != null) {
            team.setName(request.getName());
        }

        if (request.getAddress() != null) {
            team.setAddress(request.getAddress());
        }

        if (request.getSize() != null) {
            team.setSize(request.getSize());
        }

        teamRepository.save(team);
        return (team);
    }

    @Transactional(readOnly = true)
    public Iterable<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return (teamRepository
                .findById(id)
                .orElseThrow(TeamNotFoundException::new));
    }
}


