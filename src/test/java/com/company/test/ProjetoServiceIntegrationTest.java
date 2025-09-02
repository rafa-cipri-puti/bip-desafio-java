package com.company.test;

import com.company.test.domain.Projeto;
import com.company.test.repository.ProjetoRepository;
import com.company.test.service.ProjetoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProjetoServiceIntegrationTest {

    @Autowired
    ProjetoService projetoService;
    @Autowired
    ProjetoRepository projetoRepository;

    @Test
    void shouldCreateAndFetchProjeto() {
        Projeto p = new Projeto();
        p.setNome("Projeto Integracao");
        p.setDescricao("Teste de integração");
        Projeto saved = projetoService.create(p);
        assertNotNull(saved.getId());
        Projeto fetched = projetoService.get(saved.getId());
        assertEquals("Projeto Integracao", fetched.getNome());
        assertEquals("Teste de integração", fetched.getDescricao());
    }

    @Test
    void shouldListProjetos() {
        Projeto p1 = new Projeto();
        p1.setNome("P1");
        projetoService.create(p1);
        assertFalse(projetoService.list().isEmpty());
    }
}
