package art.cookedincode.spring6restmvc.listeners;

import art.cookedincode.spring6restmvc.config.KafkaConfig;
import art.cookedincode.spring6restmvcapi.events.DrinkRequestEvent;
import art.cookedincode.spring6restmvcapi.events.OrderPlacedEvent;
import art.cookedincode.spring6restmvcapi.model.BeerOrderLineDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Georgi Ivanov.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DrinkSplitterRouter {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(groupId = "DrinksSplitterRouter", topics = KafkaConfig.ORDER_PLACED_TOPIC)
    public void receive(@Payload OrderPlacedEvent orderPlacedEvent) {

        if (orderPlacedEvent.getBeerOrderDTO() == null ||
            orderPlacedEvent.getBeerOrderDTO().getBeerOrderLines() == null ||
            orderPlacedEvent.getBeerOrderDTO().getBeerOrderLines().isEmpty()) {
            log.error("Invalid order placed event");
            return;
        }

        orderPlacedEvent.getBeerOrderDTO().getBeerOrderLines().forEach(beerOrderLine -> {
           switch (beerOrderLine.getBeer().getBeerStyle()) {
               case LAGER:
                   log.debug("Splitting LAGER Order");
                   sendIceColdBeer(beerOrderLine);
                   break;
               case STOUT:
                   log.debug("Splitting STOUT Order");
                   sendCoolBeer(beerOrderLine);
                   break;
               case GOSE:
                   log.debug("Splitting GOSE Order");
                   sendColdBeer(beerOrderLine);
                   break;
               case PORTER:
                   log.debug("Splitting PORTER Order");
                   sendCoolBeer(beerOrderLine);
                   break;
               case ALE:
                   log.debug("Splitting ALE Order");
                   sendCoolBeer(beerOrderLine);
                   break;
               case WHEAT:
                   log.debug("Splitting WHEAT Order");
                   sendColdBeer(beerOrderLine);
                   break;
               case IPA:
                   log.debug("Splitting IPA Order");
                   sendCoolBeer(beerOrderLine);
                   break;
               case PALE_ALE:
                   log.debug("Splitting PALE_ALE Order");
                   sendCoolBeer(beerOrderLine);
                   break;
               case SAISON:
                   log.debug("Splitting SAISON Order");
                   sendIceColdBeer(beerOrderLine);
                   break;
           }
        });
    }

    private void sendIceColdBeer(BeerOrderLineDTO beerOrderLine) {
        kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC, DrinkRequestEvent.builder()
                .beerOrderLine(beerOrderLine)
                .build());
    }

    private void sendColdBeer(BeerOrderLineDTO beerOrderLine) {
        kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_COLD_TOPIC, DrinkRequestEvent.builder()
                .beerOrderLine(beerOrderLine)
                .build());
    }

    private void sendCoolBeer(BeerOrderLineDTO beerOrderLine) {
        kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_COOL_TOPIC, DrinkRequestEvent.builder()
                .beerOrderLine(beerOrderLine)
                .build());
    }
}
