package art.cookedincode.spring6restmvc.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.parameters.P;

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

    @NotNull
    private BeerDTO beer;

    @Min(value = 1, message = "Quantity On Hand must be greater than 0.")
    private Integer orderQuantity;
    private Integer quantityAllocated;
}
