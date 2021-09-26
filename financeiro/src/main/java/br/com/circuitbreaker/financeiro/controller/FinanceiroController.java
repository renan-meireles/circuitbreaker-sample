package br.com.circuitbreaker.financeiro.controller;

import br.com.circuitbreaker.financeiro.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;


    @GetMapping("/valor/{id}")
    public Double getProductValue(@PathVariable(value = "id") String id) throws InterruptedException {
        return financeiroService.getValor(id);
    }

}
