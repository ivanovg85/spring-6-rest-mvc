package art.cookedincode.spring6restmvc.listeners;

import art.cookedincode.spring6restmvc.config.KafkaConfig;
import art.cookedincode.spring6restmvc.repositories.BeerOrderLineRepository;
import art.cookedincode.spring6restmvcapi.events.DrinkPreparedEvent;
import art.cookedincode.spring6restmvcapi.model.BeerOrderLineStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by Georgi Ivanov.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrinkPreparedListener {

    private final BeerOrderLineRepository beerOrderLineRepository;

    @KafkaListener(groupId = "DrinkPreparedListener", topics = KafkaConfig.DRINK_PREPARED_TOPIC)
    public void listen(DrinkPreparedEvent event) {
        beerOrderLineRepository.findById(event.getBeerOrderLine().getId()).ifPresentOrElse(beerOrderLine -> {
            beerOrderLine.setOrderLineStatus(BeerOrderLineStatus.COMPLETE);
            beerOrderLineRepository.save(beerOrderLine);
        }, () -> log.error("Beer Order Line Not Found!"));
    }
}
