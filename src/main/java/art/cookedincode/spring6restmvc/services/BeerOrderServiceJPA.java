package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.mappers.BeerOrderMapper;
import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import art.cookedincode.spring6restmvc.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static art.cookedincode.spring6restmvc.services.BeerServiceJPA.buildPageRequest;

/**
 * Created by Georgi Ivanov.
 */
@Service
@RequiredArgsConstructor
public class BeerOrderServiceJPA implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public Optional<BeerOrderDTO> getById(UUID id) {
        return Optional.ofNullable(beerOrderMapper
                .beerOrderToBeerOrderDto(beerOrderRepository.findById(id)
                        .orElse(null)));
    }

    @Override
    public Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        return beerOrderRepository.findAll(buildPageRequest(pageNumber, pageSize, sort))
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }
}
