package org.education.faculty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.education.faculty.dao.entity.Faculty;
import org.education.faculty.service.FacultyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(FacultyRestController.class)
public class FacultyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    private Faculty faculty;

    @BeforeEach
    void setUp() {
        faculty = Faculty.builder()
                .id(UUID.randomUUID())
                .name("Computer Science")
        .build();
    }

    @Test
    @WithMockUser
    void shouldReturnAllFaculties() throws Exception {
        when(facultyService.findAll()).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/v1/faculty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()));

        verify(facultyService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void shouldReturnFacultyById() throws Exception {
        String facultyId = faculty.getId().toString();
        when(facultyService.findById(facultyId)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/v1/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()));

        verify(facultyService, times(1)).findById(facultyId);
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundForInvalidFacultyId() throws Exception {
        String invalidId = UUID.randomUUID().toString();
        when(facultyService.findById(invalidId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/faculty/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(facultyService, times(1)).findById(invalidId);
    }

    @Test
    @WithMockUser
    void shouldCreateNewFaculty() throws Exception {
        when(facultyService.save(Mockito.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/v1/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(faculty.getName()));

        verify(facultyService, times(1)).save(Mockito.any(Faculty.class));
    }

    @Test
    @WithMockUser
    void shouldUpdateFaculty() throws Exception {
        String facultyId = faculty.getId().toString();
        when(facultyService.update(eq(facultyId), Mockito.any(Faculty.class)))
                .thenReturn(Optional.of(faculty));

        mockMvc.perform(put("/v1/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()));

        verify(facultyService, times(1)).update(eq(facultyId), Mockito.any(Faculty.class));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenUpdatingNonexistentFaculty() throws Exception {
        String invalidId = UUID.randomUUID().toString();
        when(facultyService.update(eq(invalidId), Mockito.any(Faculty.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/faculty/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isNotFound());

        verify(facultyService, times(1)).update(eq(invalidId), Mockito.any(Faculty.class));
    }

    @Test
    @WithMockUser
    void shouldDeleteFaculty() throws Exception {
        String facultyId = faculty.getId().toString();
        when(facultyService.deleteById(facultyId)).thenReturn(true);

        mockMvc.perform(delete("/v1/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(facultyService, times(1)).deleteById(facultyId);
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenDeletingNonexistentFaculty() throws Exception {
        String invalidId = UUID.randomUUID().toString();
        when(facultyService.deleteById(invalidId)).thenReturn(false);

        mockMvc.perform(delete("/v1/faculty/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(facultyService, times(1)).deleteById(invalidId);
    }
}
