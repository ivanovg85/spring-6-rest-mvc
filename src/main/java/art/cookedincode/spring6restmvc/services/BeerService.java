package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.Beer;

import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface BeerService {

    Beer getBeerById(UUID id);
}
