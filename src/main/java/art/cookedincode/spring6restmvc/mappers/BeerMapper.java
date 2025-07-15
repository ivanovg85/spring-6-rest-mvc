package art.cookedincode.spring6restmvc.mappers;

import art.cookedincode.spring6restmvc.entities.Beer;
import art.cookedincode.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

/**
 * Created by Georgi Ivanov
 */
@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
