package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.repositories.BeerOrderRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static art.cookedincode.spring6restmvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static art.cookedincode.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH;
import static art.cookedincode.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH_ID;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Georgi Ivanov.
 */
@SpringBootTest
class BeerOrderControllerTestIT {

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;
    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void listBeerOrders() throws Exception {
        mockMvc.perform(get(BEER_ORDER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", greaterThan(0)));
    }

    @Test
    void getBeerOrderById() throws Exception {
        val beerOrder = beerOrderRepository.findAll().getFirst();

        mockMvc.perform(get(BEER_ORDER_PATH_ID, beerOrder.getId().toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerOrder.getId().toString())));
    }
}