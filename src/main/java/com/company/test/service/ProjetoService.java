package com.company.test.service;

import com.company.test.domain.Projeto;
import com.company.test.exception.ValidationException;
import com.company.test.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjetoService {
    private final ProjetoRepository repo;

    public ProjetoService(ProjetoRepository repo) {
        this.repo = repo;
    }

    public List<Projeto> list() {
        return repo.findAll();
    }

    public Projeto get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProjetoService.class);

    public Projeto create(Projeto projeto) {
        logger.info("Criando projeto: {}", projeto.getNome());
        if (projeto.getNome() != null && projeto.getNome().length() > 50) {
            throw new ValidationException("O nome do projeto não pode exceder 50 caracteres.");
        }
        if (projeto.getDescricao() != null && projeto.getDescricao().length() > 255) {
            throw new ValidationException("A descrição do projeto não pode exceder 255 caracteres.");
        }
        return repo.save(projeto);
    }

    public Projeto update(Long id, Projeto projeto) {
        if (projeto.getNome() != null && projeto.getNome().length() > 50) {
            throw new ValidationException("O nome do projeto não pode exceder 50 caracteres.");
        }
        if (projeto.getDescricao() != null && projeto.getDescricao().length() > 255) {
            throw new ValidationException("A descrição do projeto não pode exceder 255 caracteres.");
        }
        if (!repo.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado");
        }
        Projeto projetoExistente = get(id);
        projetoExistente.setNome(projeto.getNome());
        projetoExistente.setDescricao(projeto.getDescricao());
        return projetoExistente;
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado");
        }
        repo.deleteById(id);
    }
}
