package art.cookedincode.spring6restmvc.mappers;

import art.cookedincode.spring6restmvc.entities.BeerOrder;
import art.cookedincode.spring6restmvc.entities.BeerOrderLine;
import art.cookedincode.spring6restmvc.entities.BeerOrderShipment;
import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderLineDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderShipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Georgi Ivanov.
 */
@Mapper
public interface BeerOrderMapper {

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO dto);

    BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder beerOrder);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO dto);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDto(BeerOrderLineDTO dto);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderShipment beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipmentDTO dto);

    BeerOrderShipmentDTO beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipment dto);
}
