package com.nyu.taha.kafkaTest;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private NewTopic topicY;

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topicY = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
//        LOGGER.info(String.format("Order event => %s", event.toString()));

        // create Message
//        Message<KEvent> message = MessageBuilder
//                .withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC, topicY.name())
//                .build();

        EventHandler eventHandler = new KEventHandler(kafkaTemplate, topicY.name());
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(3000);
//        kafkaTemplate.send(message);
    }
}
