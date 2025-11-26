package art.cookedincode.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
@Builder
@Data
public class BeerOrderDTO {

    private UUID id;
    private Long version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String customerRef;
    private CustomerDTO customer;
    private BigDecimal paymentAmount;
    private Set<BeerOrderLineDTO> beerOrderLines;
    private BeerOrderShipmentDTO beerOrderShipment;
}
