package com.company.test.service;

import com.company.test.domain.Projeto;
import com.company.test.exception.ValidationException;
import com.company.test.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjetoServiceTest {
    ProjetoRepository repo;
    ProjetoService service;

    @BeforeEach
    void setup() {
        repo = mock(ProjetoRepository.class);
        service = new ProjetoService(repo);
    }

    @Test
    void shouldListProjetos() {
        when(repo.findAll()).thenReturn(List.of(new Projeto()));
        assertEquals(1, service.list().size());
    }

    @Test
    void shouldGetProjetoById() {
        Projeto p = new Projeto();
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        assertEquals(p, service.get(1L));
    }

    @Test
    void shouldThrowIfProjetoNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.get(2L));
    }

    @Test
    void shouldCreateProjeto() {
        Projeto p = new Projeto();
        p.setNome("Teste");
        when(repo.save(p)).thenReturn(p);
        assertEquals(p, service.create(p));
    }

    @Test
    void shouldValidateNomeLength() {
        Projeto p = new Projeto();
        p.setNome("x".repeat(51));
        assertThrows(ValidationException.class, () -> service.create(p));
    }

    @Test
    void shouldValidateDescricaoLength() {
        Projeto p = new Projeto();
        p.setNome("Teste");
        p.setDescricao("x".repeat(256));
        assertThrows(ValidationException.class, () -> service.create(p));
    }

    @Test
        void shouldUpdateProjeto() {
            Projeto old = new Projeto();
            old.setNome("Antigo");
            Projeto novo = new Projeto();
            novo.setNome("Novo");
            when(repo.existsById(1L)).thenReturn(true);
            when(repo.findById(1L)).thenReturn(Optional.of(old));
            assertEquals("Novo", service.update(1L, novo).getNome());
        }

        @Test
        void shouldThrowIfUpdateNonexistentProjeto() {
            Projeto novo = new Projeto();
            when(repo.existsById(999L)).thenReturn(false);
            assertThrows(RuntimeException.class, () -> service.update(999L, novo));
        }

        @Test
        void shouldDeleteProjeto() {
            when(repo.existsById(1L)).thenReturn(true);
            service.delete(1L);
            verify(repo, times(1)).deleteById(1L);
        }

        @Test
        void shouldThrowIfDeleteNonexistentProjeto() {
            when(repo.existsById(999L)).thenReturn(false);
            assertThrows(RuntimeException.class, () -> service.delete(999L));
        }

}
