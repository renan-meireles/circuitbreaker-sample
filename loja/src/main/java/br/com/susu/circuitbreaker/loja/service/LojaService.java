package br.com.susu.circuitbreaker.loja.service;

import br.com.susu.circuitbreaker.loja.model.Resultado;

public interface LojaService {
    Resultado getProdutoId(String id);
}
