package com.company.test;

import com.company.test.domain.Projeto;
import com.company.test.repository.ProjetoRepository;
import com.company.test.service.ProjetoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjetoServiceTest {

    @Test
    void shouldCreateAndFetchProjeto() {
        ProjetoRepository repo = Mockito.mock(ProjetoRepository.class);
        ProjetoService service = new ProjetoService(repo);

        Projeto p = new Projeto();
        p.setNome("Teste");

        when(repo.save(any())).thenAnswer(inv -> {
            Projeto saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        when(repo.findById(10L)).thenReturn(Optional.of(p));

        Projeto created = service.create(p);
        assertNotNull(created.getId());

        Projeto fetched = service.get(10L);
        assertEquals("Teste", fetched.getNome());
    }
}
