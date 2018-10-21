package pl.kamiluchnast.configclient.rest.controllers;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import pl.kamiluchnast.configclient.model.orders.MagOrder;
import pl.kamiluchnast.configclient.model.orders.SapOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

@RefreshScope
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private LinkedList<MagOrder> orders;

    @Value("${rabbitmq.queue.name.sap.orders}")
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
        rabbitClient.convertAndSend("", routingKey, sapOrder);
        return Mono.just(sapOrder);
    }

    @PostMapping("/sap")
    public Mono<String> readSapOrder(@RequestBody SapOrder sapOrder) {
        return Mono.just("ok");
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
