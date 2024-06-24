package art.cookedincode.spring6restmvc.service;

import art.cookedincode.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);
}
