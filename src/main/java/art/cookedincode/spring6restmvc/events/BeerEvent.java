package art.cookedincode.spring6restmvc.events;

import art.cookedincode.spring6restmvc.entities.Beer;
import org.springframework.security.core.Authentication;

/**
 * Created by Georgi Ivanov.
 */
public interface BeerEvent {

    Beer getBeer();

    Authentication getAuthentication();
}
