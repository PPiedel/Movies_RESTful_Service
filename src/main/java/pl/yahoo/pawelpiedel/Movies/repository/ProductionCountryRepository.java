package pl.yahoo.pawelpiedel.Movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

public interface ProductionCountryRepository extends JpaRepository<ProductionCountry, Long> {
    ProductionCountry findByName(String name);
}
