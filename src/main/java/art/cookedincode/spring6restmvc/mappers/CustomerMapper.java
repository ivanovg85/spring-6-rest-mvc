package art.cookedincode.spring6restmvc.mappers;

import art.cookedincode.spring6restmvc.entities.Customer;
import art.cookedincode.spring6restmvcapi.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Created by Georgi Ivanov
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
