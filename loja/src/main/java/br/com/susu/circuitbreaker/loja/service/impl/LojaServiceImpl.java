package br.com.susu.circuitbreaker.loja.service.impl;

import br.com.susu.circuitbreaker.loja.enumerator.Origem;
import br.com.susu.circuitbreaker.loja.model.Resultado;
import br.com.susu.circuitbreaker.loja.service.LojaService;
import com.hazelcast.map.IMap;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class LojaServiceImpl implements LojaService {

    @Value("${app.services.value.url}")
    private String url;

    private final RestTemplate template;
    private final IMap<String,Double> cache;
    private final TimeLimiter timeLimiter;
    private final CircuitBreaker circuitBreaker;

    Double valorGlobal;

    public LojaServiceImpl(RestTemplate template, IMap<String, Double> cache, TimeLimiter timeLimiter, CircuitBreaker circuitBreaker) {
        this.template = template;
        this.cache = cache;
        this.timeLimiter = timeLimiter;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Resultado getProdutoId(String id) {

        Supplier<CompletableFuture<Double>> future = () -> CompletableFuture.supplyAsync(()-> getValor(id));
        Callable<Double> timeoutDecorated = TimeLimiter.decorateFutureSupplier(timeLimiter, future);
        Callable<Double> circuitBreakerDecorated = CircuitBreaker.decorateCallable(circuitBreaker, timeoutDecorated);

        return Try.of(circuitBreakerDecorated::call).fold(
                f-> new Resultado(Origem.CACHE, getFromCache(id)),
                s-> new Resultado(Origem.SERVER, valorGlobal)
        );
    }

    public Double getValor(String id) {
        Double valor = template.getForObject(url,Double.class, id);
        cache.set(id, valor);
        valorGlobal = valor;
        return valor;
    }

    public Double getFromCache(String id) {
        return cache.get(id);
    }
}
