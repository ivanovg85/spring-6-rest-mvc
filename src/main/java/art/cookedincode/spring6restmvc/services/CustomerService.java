package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface CustomerService {

    Optional<Customer> getCustomerById(UUID id);

    List<Customer> listCustomers();

    Customer saveCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);
}
