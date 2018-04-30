package pl.yahoo.pawelpiedel.Movies.service;

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
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;
import pl.yahoo.pawelpiedel.Movies.repository.MovieRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MovieServiceImplTest {
    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    GenreRepository genreRepository;

    @MockBean
    ProductionCountryRepository productionCountryRepository;

    @MockBean
    ProductionCompanyRepository productionCompanyRepository;

    @Mock
    Movie movieMock;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllMovies_OneMovieInDB_SingletonListReturned() {
        //given
        java.util.List<Movie> persistedMovies = Collections.singletonList(movieMock);
        when(movieRepository.findAll()).thenReturn(persistedMovies);

        //when
        java.util.List<Movie> foundeMovies = movieService.getAllMovies();

        //then
        assertEquals(persistedMovies, foundeMovies);
    }

    @Test
    public void getAllMovies_MoviesInDB_MoviesListReturned() {
        //given
        Movie movie1 = new Movie();
        movie1.setTitle("test1");
        Movie movie2 = new Movie();
        movie2.setTitle("test2");
        java.util.List<Movie> persistedMovies = new ArrayList<>();
        persistedMovies.add(movie1);
        persistedMovies.add(movie2);
        when(movieRepository.findAll()).thenReturn(persistedMovies);

        //when
        java.util.List<Movie> foundedMovies = movieService.getAllMovies();

        //then
        assertEquals(persistedMovies, foundedMovies);
    }

    @Test
    public void getAllMovies_NoMoviesInDb_EmptyListReturned() {
        //given
        java.util.List<Movie> persistedMovies = Collections.emptyList();
        when(movieRepository.findAll()).thenReturn(persistedMovies);

        //when
        java.util.List<Movie> foundeMovies = movieService.getAllMovies();

        //then
        assertEquals(persistedMovies, foundeMovies);
    }

    @Test
    public void findMovieById_ExistingIdPassed_OptionalWithMovieReturned() {
        //given
        Movie persisted = new Movie();
        long testId = 1L;
        persisted.setId(testId);
        persisted.setTitle("test");
        when(movieRepository.findById(testId)).thenReturn(Optional.of(persisted));

        //when
        Optional<Movie> foundMovie = movieService.findMovieById(testId);

        //then
        assertEquals(persisted, foundMovie.get());
    }

    @Test
    public void findMovieById_NotExistingIdPassed_EmptyOptionalReturned() {
        //given
        long notExistingMovieId = 1L;

        //when
        Optional<Movie> foundMovie = movieService.findMovieById(notExistingMovieId);

        //then
        assertEquals(Optional.empty(), foundMovie);
    }

    @Test
    public void saveShouldReturnSavedMovie() {
        //given
        when(movieRepository.save(any(Movie.class))).thenReturn(movieMock);

        //when
        Movie saved = movieService.save(movieMock);

        //then
        assertEquals(movieMock, saved);
    }

    @Test
    public void saveShouldReturnNull() {
        //given
        when(movieRepository.save(null)).thenReturn(null);

        //when
        Movie saved = movieService.save(null);

        //then
        assertEquals(null, saved);
    }


    @TestConfiguration
    static class MovieServiceImplTestConfiguration {
        @Bean
        public MovieService movieService(MovieRepository movieRepository) {
            return new MovieServiceImpl(movieRepository);
        }
    }
}