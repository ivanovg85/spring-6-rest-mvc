package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.BeerOrderCreateDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
public interface BeerOrderService {

    Optional<BeerOrderDTO> getById(UUID id);

    Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize);

    BeerOrderDTO createBeerOrder(BeerOrderCreateDTO beerOrderCreateDTO);

    BeerOrderDTO updateBeerOrder(UUID beerOrderId, BeerOrderUpdateDTO beerOrderUpdateDTO);

    void deleteBeerOrder(UUID beerOrderId);
}
