package com.company.test.service;

import com.company.test.domain.Tarefa;
import com.company.test.domain.Projeto;
import com.company.test.domain.Status;
import com.company.test.domain.Prioridade;
import com.company.test.exception.ValidationException;
import com.company.test.repository.TarefaRepository;
import com.company.test.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaServiceTest {
    TarefaRepository tarefaRepo;
    ProjetoRepository projetoRepo;
    TarefaService service;

    @BeforeEach
    void setup() {
        tarefaRepo = mock(TarefaRepository.class);
        projetoRepo = mock(ProjetoRepository.class);
        service = new TarefaService(tarefaRepo, projetoRepo);
    }

    @Test
    void shouldListByProjeto() {
        when(tarefaRepo.filtrar(null, null, 1L)).thenReturn(List.of(new Tarefa()));
        assertEquals(1, service.listByProjeto(1L).size());
    }

    @Test
    void shouldFiltrarTarefas() {
        when(tarefaRepo.filtrar(Status.PENDENTE, Prioridade.ALTA, 1L)).thenReturn(List.of(new Tarefa()));
        assertEquals(1, service.filtrar(Status.PENDENTE, Prioridade.ALTA, 1L).size());
    }

    @Test
    void shouldGetTarefaById() {
        Tarefa t = new Tarefa();
        when(tarefaRepo.findById(1L)).thenReturn(Optional.of(t));
        assertEquals(t, service.get(1L));
    }

    @Test
    void shouldThrowIfTarefaNotFound() {
        when(tarefaRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.get(2L));
    }

    @Test
    void shouldCreateTarefa() {
        Projeto p = new Projeto();
        when(projetoRepo.findById(1L)).thenReturn(Optional.of(p));
        Tarefa t = new Tarefa();
        t.setTitulo("Teste");
        when(tarefaRepo.save(any())).thenReturn(t);
        assertEquals(t, service.create(1L, t));
    }

    @Test
    void shouldValidateTituloLength() {
        Tarefa t = new Tarefa();
        t.setTitulo("x".repeat(51));
        assertThrows(ValidationException.class, () -> service.create(1L, t));
    }

    @Test
    void shouldValidateDescricaoLength() {
        Tarefa t = new Tarefa();
        t.setTitulo("Teste");
        t.setDescricao("x".repeat(256));
        assertThrows(ValidationException.class, () -> service.create(1L, t));
    }

    @Test
    void shouldUpdateTarefa() {
        Tarefa old = new Tarefa();
        old.setTitulo("Antigo");
        Tarefa novo = new Tarefa();
        novo.setTitulo("Novo");
            when(tarefaRepo.existsById(1L)).thenReturn(true);
            when(tarefaRepo.findById(1L)).thenReturn(Optional.of(old));
            assertEquals("Novo", service.update(1L, novo).getTitulo());
    }

        @Test
        void shouldThrowIfUpdateNonexistentTarefa() {
            Tarefa novo = new Tarefa();
            when(tarefaRepo.existsById(999L)).thenReturn(false);
            assertThrows(RuntimeException.class, () -> service.update(999L, novo));
        }

        @Test
        void shouldDeleteTarefa() {
            when(tarefaRepo.existsById(1L)).thenReturn(true);
            service.delete(1L);
            verify(tarefaRepo, times(1)).deleteById(1L);
        }

        @Test
        void shouldThrowIfDeleteNonexistentTarefa() {
            when(tarefaRepo.existsById(999L)).thenReturn(false);
            assertThrows(RuntimeException.class, () -> service.delete(999L));
        }
}
