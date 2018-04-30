package pl.yahoo.pawelpiedel.Movies.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductionCompanyRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductionCompanyRepository productionCompanyRepository;

    @Test
    public void findByName_ExistingNamePassed_CompanyReturned() {
        //given
        String testName = "testName";
        ProductionCompany persisted = new ProductionCompany(testName);
        testEntityManager.persist(persisted);
        testEntityManager.flush();

        //when
        ProductionCompany found = productionCompanyRepository.findByName(testName);

        //then
        assertThat(persisted.getName().equals(found.getName()));
    }

    @Test
    public void findByName_NotExistingNamePassed_NullReturned() {
        //given
        String testName = "testName";

        //when
        ProductionCompany found = productionCompanyRepository.findByName(testName);

        //then
        assertNull(found);
    }
}