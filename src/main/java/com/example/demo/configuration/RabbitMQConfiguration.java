package com.example.demo.configuration;

import com.example.demo.service.CurrencyService;
import com.example.demo.service.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${queue.product}")
    private String productQueue;

    @Value("${queue.currency}")
    private String currencyQueue;

    @Value("${queue.price}")
    private String priceQueue;

    @Value("${routing-key.product}")
    private String productKey;

    @Value("${routing-key.currency}")
    private String currencyKey;

    @Value("${routing-key.price}")
    private String priceKey;

    @Value("${xchange.name}")
    private String directExchange;

    private RabbitTemplate rabbitTemplate;


    @Bean
    ProductService productService() {
        return new ProductService();
    }

    @Bean
    CurrencyService currencyService() {
        return new CurrencyService();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(this.productQueue);
    }

    @Bean
    public Queue currencyQueue() {
        return new Queue(this.currencyQueue);
    }

    @Bean
    public Queue priceQueue() {
        return new Queue(this.priceQueue);
    }

    @Bean
    public Binding productBinding(DirectExchange directExchange, Queue productQueue) {
        return BindingBuilder.bind(productQueue).to(directExchange).with(this.productKey);
    }

    @Bean
    public Binding currencyBinding(DirectExchange directExchange, Queue currencyQueue) {
        return BindingBuilder.bind(currencyQueue).to(directExchange).with(this.currencyKey);
    }

    @Bean
    public Binding priceBinding(DirectExchange priceDirectExchange, Queue priceQueue) {
        return BindingBuilder.bind(priceQueue).to(priceDirectExchange).with(this.priceKey);
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
