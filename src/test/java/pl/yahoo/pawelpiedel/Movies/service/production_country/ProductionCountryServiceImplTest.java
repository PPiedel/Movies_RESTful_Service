package pl.yahoo.pawelpiedel.Movies.service.production_country;

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
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductionCountryServiceImplTest {
    @Autowired
    private ProductionCountryService productionCountryService;

    @MockBean
    private ProductionCountryRepository productionCountryRepository;

    @Mock
    private ProductionCountry persisted;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCountryByNameShouldReturnCountry() {
        //given
        String testCountryName = "testCountryName";
        when(productionCountryRepository.findByName(testCountryName)).thenReturn(persisted);

        //when
        ProductionCountry found = productionCountryService.getByName(testCountryName);

        //then
        assertEquals(persisted, found);
    }

    @Test
    public void getCountryByNameShouldReturnNull() {
        //given
        String testCountryName = "testCountryName";
        when(productionCountryRepository.findByName(testCountryName)).thenReturn(null);

        //when
        ProductionCountry found = productionCountryService.getByName(testCountryName);

        //then
        assertNull(found);
    }

    @TestConfiguration
    static class ProductionCountryServiceImplTestConfiguration {
        @Bean
        ProductionCountryService productionCountryService(ProductionCountryRepository productionCountryRepository) {
            return new ProductionCountryServiceImpl(productionCountryRepository);
        }
    }

}