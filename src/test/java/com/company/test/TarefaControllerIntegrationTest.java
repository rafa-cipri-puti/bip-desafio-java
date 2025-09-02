package com.company.test;

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
public class TarefaControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProjetoRepository projetoRepository;

    @Test
    void shouldCreateAndListTarefa() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Controller");
        projeto = projetoRepository.save(projeto);

        String json = "{\"titulo\": \"Tarefa Controller\", \"descricao\": \"Desc\", \"status\": \"PENDENTE\", \"prioridade\": \"ALTA\"}";
        mockMvc.perform(post("/api/tasks/project/" + projeto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Tarefa Controller"));

        mockMvc.perform(get("/api/tasks/project/" + projeto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Tarefa Controller"));
    }

    @Test
    void shouldValidateTarefaFields() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Validação");
        projeto = projetoRepository.save(projeto);

        String json = "{\"titulo\": \"\", \"descricao\": \"Desc\", \"status\": \"PENDENTE\", \"prioridade\": \"ALTA\"}";
        mockMvc.perform(post("/api/tasks/project/" + projeto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
