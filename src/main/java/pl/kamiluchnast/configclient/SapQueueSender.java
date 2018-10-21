package pl.kamiluchnast.configclient;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
public class SapQueueSender {

    @RabbitListener(queues = "${rabbitmq.queue.name.sap}")
    private void sendToSap(SapOrder sapOrder) {
        System.out.println("SapOrder: " + sapOrder.getId() + ", created at:" + sapOrder.getCreatedAt());

        WebClient.create("http://localhost:8081")
                .post()
                .uri(URI.create("/api/v1/orders"))
                .body(BodyInserters.fromObject(sapOrder));
    }

}
