package com.nyu.taha.kafkaTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*This class polls the weather api every minute and pushes to the kafka topic*/
@Service
public class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Value("${weather.api.apiKey}")
    private String apiKey;

    private KafkaTemplate<String, String> kafkaTemplate;

    public WeatherService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void sendWeatherDataToKafka() {
        // Make the API call to weatherapi.com
        ResponseEntity<String> response = restTemplate.exchange(
                "http://api.weatherapi.com/v1/current.json?key="+apiKey+"&q=11201&aqi=no",
                HttpMethod.GET,
                null,
                String.class);

        // Get the response data
        String weatherData = response.getBody();
        LOGGER.warn("*****DATA SENT*******");
        kafkaTemplate.send(topicName, weatherData);
    }
}


