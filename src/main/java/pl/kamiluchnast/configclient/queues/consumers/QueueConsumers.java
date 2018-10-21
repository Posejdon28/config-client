package pl.kamiluchnast.configclient.queues.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kamiluchnast.configclient.model.orders.SapOrder;
import pl.kamiluchnast.configclient.queues.consumers.sap.SapCreateOrderResponseHandler;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Component
public class QueueConsumers {

    private WebClient sapClient;

    @Value("${url.sap.order}")
    private String sapOrderUrl;

    @Autowired
    public QueueConsumers(@Value("${url.sap}") String sapUrl) {
        this.sapClient = WebClient.create(sapUrl);
    }

    @RabbitListener(queues = "${rabbitmq.queue.name.sap.orders}")
    private void sendOrderToSap(SapOrder sapOrder) {
        System.out.println("SapOrder: " + sapOrder.getId() + ", created at:" + sapOrder.getCreatedAt());
        sapClient.post().uri(URI.create(sapOrderUrl))
                .body(BodyInserters.fromObject(sapOrder))
                .exchange()
                .flatMap(SapCreateOrderResponseHandler::handle)
                .subscribeOn(Schedulers.elastic());
    }

}
