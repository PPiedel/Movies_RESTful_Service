package pl.yahoo.pawelpiedel.Movies.service.production_country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

@Component
public class ProductionCountryServiceImpl implements ProductionCountryService {
    private final ProductionCountryRepository productionCountryRepository;

    @Autowired
    public ProductionCountryServiceImpl(ProductionCountryRepository productionCountryRepository) {
        this.productionCountryRepository = productionCountryRepository;
    }

    @Override
    public ProductionCountry getByName(String countryName) {
        return productionCountryRepository.findByName(countryName);
    }
}
