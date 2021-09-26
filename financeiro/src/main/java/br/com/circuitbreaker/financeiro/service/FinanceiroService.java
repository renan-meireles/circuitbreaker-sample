package br.com.circuitbreaker.financeiro.service;

public interface FinanceiroService {

    Double getValor(String id) throws InterruptedException;

}
