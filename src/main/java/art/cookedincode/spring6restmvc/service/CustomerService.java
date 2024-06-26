package art.cookedincode.spring6restmvc.service;

import art.cookedincode.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer);

    Boolean deleteCustomerById(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer);
}
