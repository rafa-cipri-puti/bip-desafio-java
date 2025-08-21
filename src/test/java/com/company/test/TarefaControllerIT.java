package com.company.test;

import com.company.test.domain.Projeto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldListProjects() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateProject() throws Exception {
        String json = "{\"nome\": \"Novo Projeto\", \"descricao\": \"Desc\"}";
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Novo Projeto"));
    }
}
