package pl.yahoo.pawelpiedel.Movies.service.production_company;

import org.springframework.stereotype.Service;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;

@Service
public interface ProductionCompanyService {
    ProductionCompany getCompanyByName(String companyName);
}
