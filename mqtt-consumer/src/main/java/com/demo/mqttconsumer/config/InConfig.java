package com.demo.mqttconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@Slf4j
@Configuration
public class InConfig {

    @Bean
    IntegrationFlow inboundFlow(MqttPahoMessageDrivenChannelAdapter inboundAdapter) {
        return IntegrationFlow
                .from(inboundAdapter)
                .handle((GenericHandler<String>) (payload, headers) -> {
                    // Log received messages
                    log.info("New message: " + payload);
                    // Print
                    headers.forEach((s, o) -> System.out.println(s + " = " + o));
                    return null;
                })
                .get();
    }

    @Bean
    MqttPahoMessageDrivenChannelAdapter inboundAdapter(
            @Value("${hivemq.topic}") String topic,
            MqttPahoClientFactory clientFactory
    ) {
        log.info("Topic: " + topic);
        return new MqttPahoMessageDrivenChannelAdapter("consumer", clientFactory, topic);
    }
}
