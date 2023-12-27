package com.javamsdt.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class TopicConfig {

    @Bean
    public JmsTemplate topicJmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory topicListenerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory topicListenerFactory = new DefaultJmsListenerContainerFactory();
        topicListenerFactory.setConnectionFactory(connectionFactory);
        topicListenerFactory.setPubSubDomain(true);
        // topicListenerFactory.setSubscriptionDurable(true);
        return topicListenerFactory;
    }
}
