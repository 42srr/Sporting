package kr.sporting.team;

import kr.sporting.team.domain.Team;
import kr.sporting.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository.save(Team.builder()
                .name("Lakers")
                .address("Los Angeles")
                .size(15) // 필수 컬럼 값을 지정
                .build());
        teamRepository.save(Team.builder()
                .name("Warriors")
                .address("San Francisco")
                .size(12) // 필수 컬럼 값을 지정
                .build());
    }

    @Test
    void testFindAll_ReturnsAllTeams() {
        List<Team> teams = teamRepository.findAll();
        assertThat(teams).hasSize(2);
    }

    @Test
    void testSave_PersistsTeam() {
        Team team = Team.builder()
                .name("Bulls")
                .address("Chicago")
                .size(14) // 필수 컬럼 값을 지정
                .build();
        Team savedTeam = teamRepository.save(team);

        assertThat(savedTeam.getId()).isNotNull();
        assertThat(savedTeam.getName()).isEqualTo("Bulls");
        assertThat(savedTeam.getAddress()).isEqualTo("Chicago");
    }

    @Test
    void testFindById_ReturnsTeam() {
        Team team = teamRepository.save(Team.builder()
                .name("Celtics")
                .address("Boston")
                .size(10) // 필수 컬럼 값을 지정
                .build());
        Team foundTeam = teamRepository.findById(team.getId()).orElse(null);

        assertThat(foundTeam).isNotNull();
        assertThat(foundTeam.getName()).isEqualTo("Celtics");
        assertThat(foundTeam.getAddress()).isEqualTo("Boston");
    }

    @Test
    void testDelete_RemovesTeam() {
        Team team = teamRepository.save(Team.builder()
                .name("Nets")
                .address("Brooklyn")
                .size(9) // 필수 컬럼 값을 지정
                .build());

        teamRepository.delete(team);

        assertThat(teamRepository.findById(team.getId())).isEmpty();
    }
}