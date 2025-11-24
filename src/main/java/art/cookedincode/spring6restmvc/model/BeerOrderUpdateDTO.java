package art.cookedincode.spring6restmvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
@Builder
@Data
public class BeerOrderUpdateDTO {

    private String customerRef;

    @NotNull
    private UUID customerId;

    private Set<BeerOrderLineUpdateDTO> beerOrderLines;

    private BeerOrderShipmentUpdateDTO beerOrderShipment;
}
