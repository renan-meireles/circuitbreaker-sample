package br.com.circuitbreaker.financeiro.service.impl;

import br.com.circuitbreaker.financeiro.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FinanceiroServiceImpl implements FinanceiroService {

    @Value("${app.timeout.duration}")
    private final Long timeoutDuration = 6000L;

    @Value("${app.timeout.tolerance}")
    private final Long timeoutTolerance = 5L;

    Integer chamadas = 0;

    @Override
    public Double getValor(String id) throws InterruptedException {
        Double valor = calcularPreco(id);
        simulaTrafegoRede();
        return valor;
    }

    private Double calcularPreco(String id) {
        Double precoBase = 100.00;
        Double variacao = Math.random() / 4 + 1;
        Double precoAjustado = precoBase * variacao;
        Double precoFinal = Math.ceil(precoAjustado);
        System.out.println("Preço para o produto ID: "+id+" é R$: "+ precoFinal + "- Chamada N.: "+ chamadas);
        return precoFinal;
    }

    private final void simulaTrafegoRede() throws InterruptedException {
        chamadas +=1;
        if (chamadas % 5 != 0 && chamadas > timeoutTolerance){
            Thread.sleep(timeoutDuration);
        }
    }
}
