package pl.yahoo.pawelpiedel.Movies.service.production_company;

import org.springframework.beans.factory.annotation.Autowired;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;

public class ProductionCompanyServiceImpl implements ProductionCompanyService {
    private final ProductionCompanyRepository productionCompanyRepository;

    @Autowired
    public ProductionCompanyServiceImpl(ProductionCompanyRepository productionCompanyRepository) {
        this.productionCompanyRepository = productionCompanyRepository;
    }

    @Override
    public ProductionCompany getCompanyByName(String companyName) {
        return productionCompanyRepository.findByName(companyName);
    }
}
