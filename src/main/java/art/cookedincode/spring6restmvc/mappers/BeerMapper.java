package art.cookedincode.spring6restmvc.mappers;

import art.cookedincode.spring6restmvc.entities.Beer;
import art.cookedincode.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
