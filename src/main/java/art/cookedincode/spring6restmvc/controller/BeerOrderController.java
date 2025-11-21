package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import art.cookedincode.spring6restmvc.services.BeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
@RestController
@RequiredArgsConstructor
public class BeerOrderController {

    private final BeerOrderService beerOrderService;

    public final static String BEER_ORDER_PATH = "/api/v1/beerorder";
    public final static String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderById(@PathVariable UUID beerOrderId) {
        return beerOrderService.getById(beerOrderId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listBeerOrders(@RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize) {
        return beerOrderService.listBeerOrders(pageNumber, pageSize);
    }
}
