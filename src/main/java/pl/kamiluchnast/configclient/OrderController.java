package pl.kamiluchnast.configclient;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@RefreshScope
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private LinkedList<MagOrder> orders;

    @Value("${rabbitmq.routing_key.sap}")
    private String routingKey;

    @Autowired
    private AmqpTemplate rabbitClient;

    public OrderController() {
        orders = new LinkedList<>();
    }

    @PostMapping
    public Mono<SapOrder> createOrder(@RequestBody MagOrder magOrder) {
        orders.add(magOrder);
        SapOrder sapOrder = new SapOrder(magOrder);
        rabbitClient.convertAndSend("sap", routingKey, sapOrder);
        return Mono.just(sapOrder);
    }

    @GetMapping("/sap")
    public Flux<SapOrder> getAllSapOrders() {
        return Flux.fromIterable(orders).map(SapOrder::new);
    }

    @GetMapping("")
    public Flux<MagOrder> getAllMagOrders() {
        return Flux.fromIterable(orders);
    }
}
