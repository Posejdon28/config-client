package pl.kamiluchnast.configclient.queues;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {

    @Value("${rabbitmq.queue.name.sap.orders}")
    private String sapOrderQueueName;

    @Value("${rabbitmq.queue.name.mag}")
    private String magQueueName;

    @Value("${rabbitmq.queue.name.crm}")
    private String crmQueueName;

    @Value("${rabbitmq.queue.name.whi}")
    private String whiQueueName;

    @Value("${rabbitmq.queue.name.konto}")
    private String kontoQueueName;

    @Value("${rabbitmq.queue.name.portal}")
    private String portalQueueName;

    @Value("${rabbitmq.queue.name.diagnoza}")
    private String diagnozaQueueName;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Queue sapOrderQueue() {
        return QueueBuilder.durable(sapOrderQueueName).build();
    }

    @Bean
    Queue magQueue() {
        return QueueBuilder.durable(magQueueName).build();
    }

    @Bean
    Queue crmQueue() {
        return QueueBuilder.durable(crmQueueName).build();
    }

    @Bean
    Queue whiQueue() {
        return QueueBuilder.durable(whiQueueName).build();
    }

    @Bean
    Queue kontoQueue() {
        return QueueBuilder.durable(kontoQueueName).build();
    }

    @Bean
    Queue portalQueue() {
        return QueueBuilder.durable(portalQueueName).build();
    }

    @Bean
    Queue diagnozaQueue() {
        return QueueBuilder.durable(diagnozaQueueName).build();
    }
}
