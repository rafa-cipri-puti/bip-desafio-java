package com.company.test;

import com.company.test.domain.Projeto;
import com.company.test.domain.Tarefa;
import com.company.test.domain.Status;
import com.company.test.domain.Prioridade;
import com.company.test.repository.ProjetoRepository;
import com.company.test.repository.TarefaRepository;
import com.company.test.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TarefaServiceIntegrationTest {

    @Autowired
    TarefaService tarefaService;
    @Autowired
    ProjetoRepository projetoRepository;
    @Autowired
    TarefaRepository tarefaRepository;

    @Test
    void shouldCreateAndFetchTarefa() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Tarefa");
        projeto.setDescricao("Desc");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa 1");
        tarefa.setDescricao("Desc tarefa");
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setPrioridade(Prioridade.ALTA);
        Tarefa saved = tarefaService.create(projeto.getId(), tarefa);
        assertNotNull(saved.getId());
        Tarefa fetched = tarefaService.get(saved.getId());
        assertEquals("Tarefa 1", fetched.getTitulo());
        assertEquals(projeto.getId(), fetched.getProjeto().getId());
    }

    @Test
    void shouldListTarefasByProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Listagem");
        projeto = projetoRepository.save(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Listagem");
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setPrioridade(Prioridade.MEDIA);
        tarefaService.create(projeto.getId(), tarefa);

        assertFalse(tarefaService.listByProjeto(projeto.getId()).isEmpty());
    }
}
