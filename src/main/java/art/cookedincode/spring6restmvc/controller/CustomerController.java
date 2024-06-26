package art.cookedincode.spring6restmvc.controller;

import art.cookedincode.spring6restmvc.model.CustomerDTO;
import art.cookedincode.spring6restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id) {
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {
        if (customerService.updateCustomerById(id, customer).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {
        if (customerService.patchCustomerById(id, customer).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id) {
        if (!customerService.deleteCustomerById(id))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
