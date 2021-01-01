package me.skiincraft.sql.model;

import me.skiincraft.sql.annotation.Id;
import me.skiincraft.sql.annotation.Table;

import java.time.OffsetDateTime;

@Table(value = "produtos")
public class Produto {

    @Id
    private long id;
    private String nome;
    private ProdutoType type;
    private int quantidade;
    private float valor;
    private OffsetDateTime updateDate;

    //Obrigatório ter construtor vazio
    public Produto() {}

    public Produto(long id, String nome, ProdutoType type, int quantidade, float valor, OffsetDateTime updateDate) {
        this.id = id;
        this.nome = nome;
        this.type = type;
        this.quantidade = quantidade;
        this.valor = valor;
        this.updateDate = updateDate;
    }

    public long getId() {
        return id;
    }

    public Produto setId(long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public float getValor() {
        return valor;
    }

    public Produto setValor(float valor) {
        this.valor = valor;
        return this;
    }

    public OffsetDateTime getUpdateDate() {
        return updateDate;
    }

    public Produto setUpdateDate(OffsetDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Produto setType(ProdutoType type) {
        this.type = type;
        return this;
    }

    public ProdutoType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", type=" + type +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", updateDate=" + updateDate +
                '}';
    }
}
