package com.demo.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class OutConfig {

    @Bean
    RouterFunction<ServerResponse> http(MessageChannel out) {
        return route()
                .GET("/send/{name}", request -> {
                    String name = request.pathVariable("name");
                    Message<String> message = MessageBuilder.withPayload("Hello HiveMQ from " + name + "!").build();

                    log.info(String.valueOf(message));

                    out.send(message);

                    return ServerResponse.ok().build();
                })
        .build();
    }

    @Bean
    MqttPahoMessageHandler outboundAdapter(
            @Value("${hivemq.topic}") String topic,
            MqttPahoClientFactory factory
    ) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("producer", factory);

        messageHandler.setDefaultTopic(topic);

        return messageHandler;
    }

    @Bean
    IntegrationFlow outboundFlow(MessageChannel out, MqttPahoMessageHandler outboundAdapter) {
        return IntegrationFlow
                .from(out)
                .handle(outboundAdapter)
                .get();
    }

    @Bean
    MessageChannel out() {
        return MessageChannels.direct().get();
    }
}
