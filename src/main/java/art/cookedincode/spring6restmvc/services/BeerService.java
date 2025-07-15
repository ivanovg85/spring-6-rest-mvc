package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteBeerById(UUID beerId);
}
