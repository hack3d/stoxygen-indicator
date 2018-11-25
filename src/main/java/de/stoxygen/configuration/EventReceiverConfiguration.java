package de.stoxygen.configuration;

import de.stoxygen.services.EventReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class EventReceiverConfiguration implements RabbitListenerConfigurer {
    @Bean
    public Exchange eventExchange() {
        return new TopicExchange("STO2-Exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("indicatorQueue");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange evenExchange) {
        return BindingBuilder.bind(queue).to(evenExchange).with("indicator.calculate");
    }

    @Bean
    public EventReceiver eventReceiver() {
        return new EventReceiver();
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(mappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
