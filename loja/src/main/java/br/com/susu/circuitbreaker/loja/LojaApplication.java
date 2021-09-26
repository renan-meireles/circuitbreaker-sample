package br.com.susu.circuitbreaker.loja;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class LojaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LojaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IMap<String, Double> cache(HazelcastInstance hazelcast) {
        return hazelcast.getMap("preços");
    }

    @Bean
    public TimeLimiter timeLimiter() {
        return TimeLimiter.of(Duration.ofSeconds(2L));
    }

    @Bean
    public CircuitBreaker circuitBreaker(){
        return CircuitBreaker.of("circuit-breaker",
                CircuitBreakerConfig.custom()
                        .failureRateThreshold(80)
                        .slidingWindow(5, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .waitDurationInOpenState(Duration.ofSeconds(20L))
                        .build());
    }

}
