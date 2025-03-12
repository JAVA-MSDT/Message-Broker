package com.javamsdt.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
@Configuration
public class QueueConfig {
    @Bean
    public JmsTemplate queueJmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory queueListenerFactory = new DefaultJmsListenerContainerFactory();
        queueListenerFactory.setConnectionFactory(connectionFactory);
        return queueListenerFactory;
    }
}
