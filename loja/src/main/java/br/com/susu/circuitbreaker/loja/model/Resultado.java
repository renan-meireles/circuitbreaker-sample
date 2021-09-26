package br.com.susu.circuitbreaker.loja.model;

import br.com.susu.circuitbreaker.loja.enumerator.Origem;

public class Resultado {

    private Origem origem;
    private Double valor;

    public Resultado() {
    }

    public Resultado(Origem origem, Double valor) {
        this.origem = origem;
        this.valor = valor;
    }

    public Origem getOrigem() {
        return origem;
    }

    public Double getValor() {
        return valor;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
