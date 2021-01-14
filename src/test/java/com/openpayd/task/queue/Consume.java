package com.openpayd.task.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consume {

    @Test
    public void consume() throws TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            while(true){
                Thread.sleep(300);
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");

                    System.out.println(" [x] Received '" + message + "'");
                };
                channel.basicConsume("my-first-rabbitmq-queue", true, deliverCallback, consumerTag -> { });
            }
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }catch (IOException e) {
            throw new RuntimeException("Dijital Sıraya erişilemedi. Dijital Sıranın çalıştığından emin olun!");
        }

    }
}

