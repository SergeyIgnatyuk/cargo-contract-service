package by.cargocontractservice.event;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendObjectMessage(String value) {
        String topic = "logistic-main-topic";
        int partition = 1;
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, null, value);
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(record);
        result.thenAccept(message -> System.out.printf("Kafka message has been sent: \"%s\"\n", message.getProducerRecord().value()));
    }
}
