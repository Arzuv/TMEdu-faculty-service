package org.education.faculty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.education.faculty.dao.entity.Discipline;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.service.DisciplineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(DisciplineRestController.class)
public class DisciplineRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisciplineService disciplineService;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    private Discipline discipline;

    @BeforeEach
    void setUp() {
        discipline = Discipline.builder()
                .id(UUID.randomUUID())
                .facultyId(UUID.randomUUID())
                .name("Mathematics")
                .build();
    }

    @Test
    @WithMockUser
    void getAllDisciplines_ShouldReturnListOfDisciplines() throws Exception {
        List<Discipline> disciplines = Collections.singletonList(discipline);
        when(disciplineService.findAll()).thenReturn(disciplines);
        mockMvc.perform(get("/v1/discipline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(discipline.getId().toString().toString()))
                .andExpect(jsonPath("$[0].name").value(discipline.getName()));
    }

    @Test
    @WithMockUser
    void getAllDisciplines_ShouldReturnListOfDisciplinesByFaculty() throws Exception {
        List<Discipline> disciplines = Collections.singletonList(discipline);
        when(disciplineService.findAllByFacultyId(anyString())).thenReturn(disciplines);
        String url = String.format("/v1/discipline/byFaculty/%s", discipline.getFacultyId().toString());
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(discipline.getId().toString().toString()))
                .andExpect(jsonPath("$[0].name").value(discipline.getName()));
    }

    @Test
    @WithMockUser
    void getDisciplineById_ShouldReturnDiscipline() throws Exception {
        when(disciplineService.findById(anyString())).thenReturn(Optional.of(discipline));

        mockMvc.perform(get("/v1/discipline/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.name").value(discipline.getName()));
    }

    @Test
    @WithMockUser
    void getDisciplineById_NotFound() throws Exception {
        when(disciplineService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/discipline/{id}", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createDiscipline_ShouldReturnCreatedDiscipline() throws Exception {
        when(disciplineService.save(any(Discipline.class))).thenReturn(discipline);

        mockMvc.perform(post("/v1/discipline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discipline)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.name").value(discipline.getName()));
    }

    @Test
    @WithMockUser
    void updateDiscipline_ShouldReturnUpdatedDiscipline() throws Exception {
        when(disciplineService.update(anyString(), any(Discipline.class))).thenReturn(Optional.of(discipline));

        mockMvc.perform(put("/v1/discipline/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discipline)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(discipline.getId().toString()))
                .andExpect(jsonPath("$.name").value(discipline.getName()));
    }

    @Test
    @WithMockUser
    void updateDiscipline_NotFound() throws Exception {
        when(disciplineService.update(anyString(), any(Discipline.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/discipline/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(discipline)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteDiscipline_ShouldReturnNoContent() throws Exception {
        when(disciplineService.deleteById(anyString())).thenReturn(true);

        mockMvc.perform(delete("/v1/discipline/{id}", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteDiscipline_NotFound() throws Exception {
        when(disciplineService.deleteById(anyString())).thenReturn(false);

        mockMvc.perform(delete("/v1/discipline/{id}", "2"))
                .andExpect(status().isNotFound());
    }
}
