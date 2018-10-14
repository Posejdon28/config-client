package pl.kamiluchnast.configclient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
@RequestMapping("/rest")
public class TestController {


    @Value("${message: Default value}")
    private String message;

    @GetMapping
    public Mono<String> getTestMessage() {
        return Mono.just(message);
    }
}
