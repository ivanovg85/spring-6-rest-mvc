package art.cookedincode.spring6restmvc.events;

import art.cookedincode.spring6restmvc.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

/**
 * Created by Georgi Ivanov.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerUpdatedEvent implements BeerEvent {

    private Beer beer;

    private Authentication authentication;
}
