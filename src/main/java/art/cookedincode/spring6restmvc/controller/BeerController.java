package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.model.Beer;
import art.cookedincode.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {

        log.debug("Get Beer By Id - in controller");

        return beerService.getBeerById(id);
    }
}
