package art.cookedincode.spring6restmvc.repositories;

import art.cookedincode.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
