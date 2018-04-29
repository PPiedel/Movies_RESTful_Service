package pl.yahoo.pawelpiedel.Movies.service.production_country;

import org.springframework.stereotype.Service;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

@Service
public interface ProductionCountryService {
    public ProductionCountry getByName(String countryName);
}
