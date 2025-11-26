package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.services.BeerOrderService;
import art.cookedincode.spring6restmvcapi.model.BeerOrderCreateDTO;
import art.cookedincode.spring6restmvcapi.model.BeerOrderDTO;
import art.cookedincode.spring6restmvcapi.model.BeerOrderUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping(BEER_ORDER_PATH)
    public ResponseEntity<Void> createBeerOrder(@RequestBody BeerOrderCreateDTO beerOrderCreateDTO) {
        BeerOrderDTO beerOrder = beerOrderService.createBeerOrder(beerOrderCreateDTO);

        return ResponseEntity.created(URI.create(BEER_ORDER_PATH + "/" + beerOrder.getId().toString())).build();
    }

    @PutMapping(BEER_ORDER_PATH_ID)
    public ResponseEntity<BeerOrderDTO> updateBeerOrder(@PathVariable UUID beerOrderId,
                                                @RequestBody BeerOrderUpdateDTO beerOrderUpdateDTO) {
        return ResponseEntity.ok(beerOrderService.updateBeerOrder(beerOrderId, beerOrderUpdateDTO));
    }

    @DeleteMapping(BEER_ORDER_PATH_ID)
    public ResponseEntity<Void> deleteBeerOrder(@PathVariable UUID beerOrderId) {
        beerOrderService.deleteBeerOrder(beerOrderId);
        return ResponseEntity.noContent().build();
    }
}
