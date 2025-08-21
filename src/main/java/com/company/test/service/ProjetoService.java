package com.company.test.service;

import com.company.test.domain.Projeto;
import com.company.test.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjetoService {
    private final ProjetoRepository repo;

    public ProjetoService(ProjetoRepository repo) { this.repo = repo; }

    public List<Projeto> list() { return repo.findAll(); }
    public Projeto get(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Projeto não encontrado")); }
    public Projeto create(Projeto p) { return repo.save(p); }
    public Projeto update(Long id, Projeto p) {
        Projeto existing = get(id);
        existing.setNome(p.getNome());
        existing.setDescricao(p.getDescricao());
        return existing;
    }
    public void delete(Long id) { repo.deleteById(id); }
}
