package com.openpayd.task.queue;

import com.openpayd.task.service.RateService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Produce {
    @Autowired
    RateService rateService;

    @Test
    public void produce() throws  TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            String message = "Hello World!";
            channel.basicPublish("", "my-first-rabbitmq-queue", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException("Dijital Sıraya erişilemedi. Dijital Sıranın çalıştığından emin olun!");
        }
    }
}
