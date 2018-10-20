package pl.kamiluchnast.configclient;


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

    public OrderController() {
        orders = new LinkedList<>();
    }

    @PostMapping
    public Mono<SapOrder> createOrder(@RequestBody MagOrder magOrder) {
        orders.add(magOrder);
        return Mono.just(new SapOrder(magOrder));
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
