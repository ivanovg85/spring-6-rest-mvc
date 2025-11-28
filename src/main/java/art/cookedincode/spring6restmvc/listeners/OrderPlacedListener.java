package art.cookedincode.spring6restmvc.listeners;

import art.cookedincode.spring6restmvc.config.KafkaConfig;
import art.cookedincode.spring6restmvcapi.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Georgi Ivanov.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Async
    @EventListener
    public void listen(OrderPlacedEvent orderPlacedEvent) {

        log.debug("Order Placed Event received");
        kafkaTemplate.send(KafkaConfig.ORDER_PLACED_TOPIC, orderPlacedEvent);
    }
}
