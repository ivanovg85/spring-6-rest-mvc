package art.cookedincode.spring6restmvc.repositories;

import art.cookedincode.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
