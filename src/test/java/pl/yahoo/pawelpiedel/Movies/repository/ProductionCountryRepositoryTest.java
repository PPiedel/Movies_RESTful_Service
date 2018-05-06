package pl.yahoo.pawelpiedel.Movies.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ProductionCountryRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductionCountryRepository productionCountryRepository;

    @Test
    public void findByName_ExistingNamePassed_CountryReturned() {
        //given
        String testCountryName = "testCountryName";
        ProductionCountry persisted = new ProductionCountry(testCountryName);
        testEntityManager.persist(persisted);
        testEntityManager.flush();

        //when
        ProductionCountry found = productionCountryRepository.findByName(testCountryName);

        //then
        assertThat(found.getName().equals(persisted.getName()));
    }

    @Test
    public void findByName_NotExistingNamePassed_NullReturned() {
        //given
        String testCountryName = "testCountryName";

        //when
        ProductionCountry found = productionCountryRepository.findByName(testCountryName);

        //then
        assertNull(found);
    }
}