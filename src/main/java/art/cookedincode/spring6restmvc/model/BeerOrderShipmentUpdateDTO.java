package art.cookedincode.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Georgi Ivanov.
 */
@Builder
@Data
public class BeerOrderShipmentUpdateDTO {

    @NotBlank
    private String trackingNumber;
}
