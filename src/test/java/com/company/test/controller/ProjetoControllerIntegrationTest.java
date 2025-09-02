package com.company.test.controller;

import com.company.test.domain.Projeto;
import com.company.test.repository.ProjetoRepository;
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
public class ProjetoControllerIntegrationTest {
    @Test
    void shouldGetProjetoById() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Get");
        projeto = projetoRepository.save(projeto);

        mockMvc.perform(get("/api/projects/" + projeto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projeto.getId()))
                .andExpect(jsonPath("$.nome").value("Projeto Get"));
    }

    @Test
    void shouldReturnNotFoundWhenGetNonexistentProjeto() throws Exception {
        mockMvc.perform(get("/api/projects/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Projeto não encontrado"));
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProjetoRepository projetoRepository;

    @Test
    void shouldCreateAndListProjeto() throws Exception {
    String json = "{\"nome\": \"Projeto Teste\", \"descricao\": \"Desc\"}";
    mockMvc.perform(post("/api/projects")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.nome").value("Projeto Teste"));

    mockMvc.perform(get("/api/projects"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[?(@.nome == 'Projeto Teste')]").exists());
    }

    @Test
    void shouldValidateProjetoFields() throws Exception {
        String json = "{\"nome\": \"\", \"descricao\": \"Desc\"}";
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void shouldUpdateProjeto() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Antigo");
        projeto = projetoRepository.save(projeto);

        String json = "{\"nome\": \"Projeto Novo\", \"descricao\": \"Nova Desc\"}";
        mockMvc.perform(put("/api/projects/" + projeto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Projeto Novo"));
    }

    @Test
    void shouldDeleteProjeto() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Delete");
        projeto = projetoRepository.save(projeto);

        mockMvc.perform(delete("/api/projects/" + projeto.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeleteNonexistentProjeto() throws Exception {
    mockMvc.perform(delete("/api/projects/999999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Projeto não encontrado"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdateNonexistentProjeto() throws Exception {
    String json = "{\"nome\": \"Projeto Inexistente\", \"descricao\": \"Desc\"}";
    mockMvc.perform(put("/api/projects/999999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Projeto não encontrado"));
    }
}
