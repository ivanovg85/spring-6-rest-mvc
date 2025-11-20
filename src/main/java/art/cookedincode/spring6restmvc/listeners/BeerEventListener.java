package art.cookedincode.spring6restmvc.listeners;

import art.cookedincode.spring6restmvc.events.*;
import art.cookedincode.spring6restmvc.mappers.BeerMapper;
import art.cookedincode.spring6restmvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Georgi Ivanov.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerEventListener {

    private final BeerAuditRepository beerAuditRepository;
    private final BeerMapper beerMapper;

    @Async
    @EventListener
    public void listen(BeerEvent event) {
        val beerAudit = beerMapper.beerToBeerAudit(event.getBeer());
        String eventType =
                switch (event) {
                    case BeerCreatedEvent beerCreatedEvent -> "BEER_CREATED";
                    case BeerPatchedEvent beerPatchedEvent -> "BEER_PATCHED";
                    case BeerUpdatedEvent beerUpdatedEvent -> "BEER_UPDATED";
                    case BeerDeletedEvent beerDeletedEvent -> "BEER_DELETED";
                    default -> "UNKNOWN";
                };

        beerAudit.setAuditEventType(eventType);

        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
        log.debug("BeerAudit with type {} saved: {}", eventType, savedBeerAudit.getId());
    }
}
