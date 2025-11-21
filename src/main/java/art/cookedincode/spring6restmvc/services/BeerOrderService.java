package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
public interface BeerOrderService {

    Optional<BeerOrderDTO> getById(UUID id);

    Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize);
}
