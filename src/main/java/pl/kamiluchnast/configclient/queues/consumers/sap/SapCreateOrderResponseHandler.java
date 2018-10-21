package pl.kamiluchnast.configclient.queues.consumers.sap;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class SapCreateOrderResponseHandler {

    public static Mono<String> handle(ClientResponse clientResponse) {
        Mono<String> response = clientResponse.bodyToMono(String.class);
        String block = response.block();
        System.out.println("RESPONSE: " + block);
        return Mono.just(block);
    }
}
