package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface CustomerService {

    Customer getCustomerById(UUID id);

    List<Customer> listCustomers();

    Customer saveCustomer(Customer customer);
}
