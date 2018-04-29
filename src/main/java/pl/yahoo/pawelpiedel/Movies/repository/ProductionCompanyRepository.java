package pl.yahoo.pawelpiedel.Movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;

public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Long> {
    ProductionCompany findByName(String name);
}
