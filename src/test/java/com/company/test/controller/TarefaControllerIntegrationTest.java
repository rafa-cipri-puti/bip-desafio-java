package com.company.test.controller;

import com.company.test.domain.Projeto;
import com.company.test.domain.Tarefa;
import com.company.test.repository.ProjetoRepository;
import com.company.test.repository.TarefaRepository;
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
    @Test
    void shouldGetTarefaById() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Get Tarefa");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Get");
        tarefa.setStatus(com.company.test.domain.Status.PENDENTE);
        tarefa.setPrioridade(com.company.test.domain.Prioridade.ALTA);
        tarefa.setProjeto(projeto);
        tarefa = tarefaRepository.save(tarefa);

        mockMvc.perform(get("/api/tasks/" + tarefa.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tarefa.getId()))
                .andExpect(jsonPath("$.titulo").value("Tarefa Get"));
    }

    @Test
    void shouldReturnNotFoundWhenGetNonexistentTarefa() throws Exception {
        mockMvc.perform(get("/api/tasks/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Tarefa não encontrada"));
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProjetoRepository projetoRepository;
    @Autowired
    TarefaRepository tarefaRepository;

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

    @Test
    void shouldUpdateTarefa() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Update");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Antiga");
        tarefa.setStatus(com.company.test.domain.Status.PENDENTE);
        tarefa.setPrioridade(com.company.test.domain.Prioridade.ALTA);
        tarefa.setProjeto(projeto);
        tarefa = tarefaRepository.save(tarefa);

        String json = "{\"titulo\": \"Tarefa Nova\", \"descricao\": \"Nova Desc\", \"status\": \"CONCLUIDA\", \"prioridade\": \"BAIXA\"}";
        mockMvc.perform(put("/api/tasks/" + tarefa.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tarefa Nova"));
    }

    @Test
    void shouldDeleteTarefa() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Delete");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Delete");
        tarefa.setStatus(com.company.test.domain.Status.PENDENTE);
        tarefa.setPrioridade(com.company.test.domain.Prioridade.ALTA);
        tarefa.setProjeto(projeto);
        tarefa = tarefaRepository.save(tarefa);

        mockMvc.perform(delete("/api/tasks/" + tarefa.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFilterTarefas() throws Exception {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Filtro");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Filtro");
        tarefa.setStatus(com.company.test.domain.Status.PENDENTE);
        tarefa.setPrioridade(com.company.test.domain.Prioridade.ALTA);
        tarefa.setProjeto(projeto);
        tarefaRepository.save(tarefa);

        mockMvc.perform(get("/api/tasks?status=PENDENTE&prioridade=ALTA&projetoId=" + projeto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Tarefa Filtro"));
    }

    @Test
    void shouldReturnNotFoundWhenDeleteNonexistentTarefa() throws Exception {
    mockMvc.perform(delete("/api/tasks/999999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Tarefa não encontrada"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdateNonexistentTarefa() throws Exception {
    String json = "{\"titulo\": \"Tarefa Inexistente\", \"descricao\": \"Desc\", \"status\": \"PENDENTE\", \"prioridade\": \"ALTA\"}";
    mockMvc.perform(put("/api/tasks/999999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Tarefa não encontrada"));
    }
}
