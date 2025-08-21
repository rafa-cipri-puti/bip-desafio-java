package com.company.test.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status = Status.PENDENTE;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Prioridade prioridade = Prioridade.MEDIA;

    @ManyToOne(optional = false)
    private Projeto projeto;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}
