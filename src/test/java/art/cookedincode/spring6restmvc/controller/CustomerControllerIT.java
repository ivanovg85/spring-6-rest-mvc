package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.entities.Customer;
import art.cookedincode.spring6restmvc.mappers.CustomerMapper;
import art.cookedincode.spring6restmvc.model.CustomerDTO;
import art.cookedincode.spring6restmvc.repositories.BeerOrderRepository;
import art.cookedincode.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Georgi Ivanov
 */
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Test
    void testPatchCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerPatchById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerById() {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customerRepository.findAll().getFirst());
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerPatchById(customerDTO.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customerDTO.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void testDeleteCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();

        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testUpdateCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateCustomerById() {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customerRepository.findAll().getFirst());
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateById(customerDTO.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customerDTO.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertNotNull(customer);
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().getFirst();

        assertThat(customerController.getCustomerById(customer.getId())).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerOrderRepository.deleteAll();
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}