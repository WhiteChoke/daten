package dev.whitechoke.deskService.config;

import dev.whitechoke.commonLibs.kafka.ProfileCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.CompositeKafkaStreamsCustomizer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    public DefaultKafkaConsumerFactory<Long, ProfileCreatedEvent> profileCreatedEventConsumerFactory(
            KafkaProperties properties
    ) {
        Map<String, Object> consumerProps = properties.buildConsumerProperties();
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        consumerProps.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "dev.whitechoke.commondLibs.kafka");
        return new DefaultKafkaConsumerFactory<>(consumerProps);
    }

    @Bean
    public KafkaListenerContainerFactory<?> profileCreatedEventListenerContainerFactory(
            DefaultKafkaConsumerFactory<Long, ProfileCreatedEvent> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, ProfileCreatedEvent>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        return factory;
    }
}
