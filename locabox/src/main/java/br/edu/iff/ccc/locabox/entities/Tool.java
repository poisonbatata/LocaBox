package br.edu.iff.ccc.locabox.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private String condicao;
    private String disponibilidade;
    private String fotos;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

    public Tool() {}

    public Tool(Long id, String nome, String descricao, String categoria, double preco, String condicao, String disponibilidade, String fotos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.condicao = condicao;
        this.disponibilidade = disponibilidade;
        this.fotos = fotos;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public String getCondicao() { return condicao; }
    public void setCondicao(String condicao) { this.condicao = condicao; }
    public String getDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(String disponibilidade) { this.disponibilidade = disponibilidade; }
    public String getFotos() { return fotos; }
    public void setFotos(String fotos) { this.fotos = fotos; }
}