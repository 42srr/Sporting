package kr.sporting.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.sporting.H2TestDatabaseConfig;
import kr.sporting.team.controller.TeamController;
import kr.sporting.team.domain.Team;
import kr.sporting.team.dto.CreateTeamRequest;
import kr.sporting.team.dto.UpdateTeamRequest;
import kr.sporting.team.repository.TeamRepository;
import kr.sporting.team.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
@MockitoBean(types = {JpaMetamodelMappingContext.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(H2TestDatabaseConfig.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TeamService teamService;
//
    @MockitoBean
    private TeamRepository teamRepository;

    private Team mockTeam;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
        mockTeam = Team.builder()
                .teamName("Mock Team")
                .address("Mock Address")
                .teamSize(5)
                .build();
//        mockTeam.setId(1L);
    }

    @Test
    void testCreateTeam_ReturnsCreatedTeam() throws Exception {
        // given
        CreateTeamRequest request = CreateTeamRequest.builder()
                .teamName("New Team")
                .address("New Address")
                .teamSize(10)
                .build();

        Mockito.when(teamService.createTeam(any(Team.class))).thenAnswer(invocation -> {
            Team team = invocation.getArgument(0);
            team.setId(1L);
            return team;
        });

        // when & then
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        Mockito.verify(teamService).createTeam(any(Team.class));
    }

    @Test
    void testGetTeamById_ReturnsValidTeam() throws Exception {
        // given
        Mockito.when(teamService.getTeamById(1L)).thenReturn(mockTeam);

        // when & then
        mockMvc.perform(get("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockTeam.getId()))
                .andExpect(jsonPath("$.teamName").value(mockTeam.getTeamName()))
                .andExpect(jsonPath("$.address").value(mockTeam.getAddress()))
                .andExpect(jsonPath("$.teamSize").value(mockTeam.getTeamSize()));

        Mockito.verify(teamService).getTeamById(1L);
    }

    @Test
    void testUpdateTeam_ReturnsUpdatedTeam() throws Exception {
        // given
        UpdateTeamRequest request = UpdateTeamRequest.builder()
                .teamName("Updated Team")
                .address("Updated Address")
                .teamSize(15)
                .build();

        Mockito.when(teamService.updateTeam(eq(1L), any(UpdateTeamRequest.class)))
                .thenReturn(Team.builder()
                        .id(1L)
                        .teamName(request.getTeamName())
                        .address(request.getAddress())
                        .teamSize(request.getTeamSize())
                        .build());

        // when & then
        mockMvc.perform(put("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.teamName").value(request.getTeamName()))
                .andExpect(jsonPath("$.address").value(request.getAddress()))
                .andExpect(jsonPath("$.teamSize").value(request.getTeamSize()));

        Mockito.verify(teamService).updateTeam(eq(1L), any(UpdateTeamRequest.class));
    }

    @Test
    void testDeleteTeam_ReturnsOk() throws Exception {
        // given
        Mockito.doNothing().when(teamService).deleteTeam(eq(1L));

        // when & then
        mockMvc.perform(delete("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(teamService).deleteTeam(eq(1L));
    }

    @Test
    void testGetTeams_ReturnsAllTeams() throws Exception {
        // given
        List<Team> teams = new ArrayList<>();
        teams.add(mockTeam);
        teams.add(Team.builder()
                .id(2L)
                .teamName("Second Team")
                .address("Second Address")
                .teamSize(8)
                .build());

        Mockito.when(teamService.getTeams()).thenReturn(teams);

        // when & then
        mockMvc.perform(get("/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockTeam.getId()))
                .andExpect(jsonPath("$[0].teamName").value(mockTeam.getTeamName()))
                .andExpect(jsonPath("$[0].address").value(mockTeam.getAddress()))
                .andExpect(jsonPath("$[0].teamSize").value(mockTeam.getTeamSize()))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].teamName").value("Second Team"))
                .andExpect(jsonPath("$[1].address").value("Second Address"))
                .andExpect(jsonPath("$[1].teamSize").value(8));

        Mockito.verify(teamService).getTeams();
    }

    @Test
    void testCreateTeam_InvalidRequest_ReturnsBadRequest() throws Exception {
        // given: invalid request (missing required fields)
        CreateTeamRequest invalidRequest = CreateTeamRequest.builder()
                .teamName(null)
                .address("New Address")
                .teamSize(0)
                .build();

        // when & then
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTeam_InvalidRequest_ReturnsBadRequest() throws Exception {
        // given: invalid request data
        UpdateTeamRequest invalidRequest = UpdateTeamRequest.builder()
                .teamName("")
                .address("")
                .teamSize(1)
                .build();

        // when & then
        mockMvc.perform(put("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}