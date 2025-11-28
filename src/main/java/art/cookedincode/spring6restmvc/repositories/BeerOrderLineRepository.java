package art.cookedincode.spring6restmvc.repositories;

import art.cookedincode.spring6restmvc.entities.BeerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Georgi Ivanov.
 */
public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {
}
