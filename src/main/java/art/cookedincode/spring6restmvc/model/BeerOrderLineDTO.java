package art.cookedincode.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
@Builder
@Data
public class BeerOrderLineDTO {

    private UUID id;
    private Long version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private BeerDTO beer;
    private Integer orderQuantity;
    private Integer quantityAllocated;
}
