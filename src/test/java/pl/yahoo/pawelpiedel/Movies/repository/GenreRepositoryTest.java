package pl.yahoo.pawelpiedel.Movies.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void findByName_ExistingNamePassed_GenreReturned() {
        //given
        String testName = "comedy";
        Genre persisted = new Genre(testName);
        entityManager.persist(persisted);
        entityManager.flush();

        //when
        Genre found = genreRepository.findByName(testName);

        //then
        assertThat(found.getName()).isEqualTo(persisted.getName());
    }

    @Test
    public void findByName_NotExistingNamePassed_NullReturned() {
        //given
        String testName = "comedy";

        //when
        Genre found = genreRepository.findByName(testName);

        //then
        assertNull(found);
    }
}