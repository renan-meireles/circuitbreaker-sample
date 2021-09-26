package br.com.susu.circuitbreaker.loja.controller;

import br.com.susu.circuitbreaker.loja.model.Resultado;
import br.com.susu.circuitbreaker.loja.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @GetMapping("/produto/{id}")
    public Resultado getProdutoById(@PathVariable(value = "id") String id){
        return lojaService.getProdutoId(id);
    }

}
