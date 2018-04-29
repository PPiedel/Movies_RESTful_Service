package pl.yahoo.pawelpiedel.Movies.service.genre;

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
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GenreServiceImplTest {
    @Autowired
    private GenreService genreService;

    @MockBean
    private GenreRepository genreRepository;

    @Mock
    Genre persisted;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findByNameShouldReturnGenre() {
        //given
        String testName = "testName";
        when(genreRepository.findByName(testName)).thenReturn(persisted);

        //when
        Genre foundGenre = genreService.findByName(testName);

        //then
        assertEquals(persisted, foundGenre);
    }

    @Test
    public void findByNameShouldReturnNull() {
        //given
        String testName = "testName";
        when(genreRepository.findByName(testName)).thenReturn(null);

        //when
        Genre foundGenre = genreService.findByName(testName);

        //then
        assertEquals(null, foundGenre);
    }

    @TestConfiguration
    static class GenreServiceImplTestConfiguration {
        @Bean
        GenreService genreService(GenreRepository genreRepository) {
            return new GenreServiceImpl(genreRepository);
        }
    }
}