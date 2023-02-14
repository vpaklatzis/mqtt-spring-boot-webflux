package com.demo.mqttconsumer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@Slf4j
@SpringBootApplication
public class MqttConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttConsumerApplication.class, args);
	}

	@Bean
	MqttPahoClientFactory clientFactory(@Value("${hivemq.uri}") String host) {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();

		log.info("Host: " + host);

		options.setServerURIs(new String[]{host});
		factory.setConnectionOptions(options);

		return factory;
	}
}
