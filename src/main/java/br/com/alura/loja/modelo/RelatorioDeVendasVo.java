package br.com.alura.loja.modelo;

import java.time.LocalDate;

public class RelatorioDeVendasVo {
    private String nome;
    private Long quantidade;
    private LocalDate data;

    public RelatorioDeVendasVo() {}

    public RelatorioDeVendasVo(String nome, Long quantidade, LocalDate data) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public LocalDate getData() {
        return data;
    }
}
