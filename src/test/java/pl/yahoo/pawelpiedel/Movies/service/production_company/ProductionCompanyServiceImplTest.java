package pl.yahoo.pawelpiedel.Movies.service.production_company;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductionCompanyServiceImplTest {
    @Autowired
    ProductionCompanyService productionCompanyService;

    @MockBean
    ProductionCompanyRepository productionCompanyRepository;

    @Mock
    private ProductionCompany persistedCompany;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCompanyByNameShouldReturnPersistedCompany() {
        //given
        String testName = "testName";
        when(productionCompanyRepository.findByName(testName)).thenReturn(persistedCompany);

        //when
        ProductionCompany found = productionCompanyService.getCompanyByName(testName);

        //then
        assertEquals(persistedCompany, found);
    }

    @Test
    public void getCompanyByNameShouldReturnNullValue() {
        //given
        String testName = "testName";
        when(productionCompanyRepository.findByName(testName)).thenReturn(null);

        //when
        ProductionCompany found = productionCompanyService.getCompanyByName(testName);

        //then
        assertEquals(null, found);
    }

    @TestConfiguration
    static class ProductionCompanyServiceImplTestConfiguration {
        @Bean
        ProductionCompanyService productionCompanyService(ProductionCompanyRepository productionCompanyRepository) {
            return new ProductionCompanyServiceImpl(productionCompanyRepository);
        }
    }
}