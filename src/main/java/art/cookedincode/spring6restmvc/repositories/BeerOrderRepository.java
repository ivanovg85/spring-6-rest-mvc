package art.cookedincode.spring6restmvc.repositories;

import art.cookedincode.spring6restmvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
