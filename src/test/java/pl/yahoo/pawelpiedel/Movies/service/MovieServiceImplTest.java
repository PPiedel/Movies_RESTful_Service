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
import pl.yahoo.pawelpiedel.Movies.repository.MovieRepository;
import pl.yahoo.pawelpiedel.Movies.service.movie.MovieService;
import pl.yahoo.pawelpiedel.Movies.service.movie.MovieServiceImpl;

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

    @Mock
    Movie movieMock;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllMoviesShouldReturnExactlyOneMovie() {
        //given
        java.util.List<Movie> persistedMovies = Collections.singletonList(movieMock);
        when(movieRepository.findAll()).thenReturn(persistedMovies);

        //when
        java.util.List<Movie> foundeMovies = movieRepository.findAll();

        //then
        assertEquals(persistedMovies, foundeMovies);
    }

    @Test
    public void getAllMoviesShouldReturnMovieList() {
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
        java.util.List<Movie> foundedMovies = movieRepository.findAll();

        //then
        assertEquals(persistedMovies, foundedMovies);
    }

    @Test
    public void getAllMoviesShouldEmptyList() {
        //given
        java.util.List<Movie> persistedMovies = Collections.emptyList();
        when(movieRepository.findAll()).thenReturn(persistedMovies);

        //when
        java.util.List<Movie> foundeMovies = movieRepository.findAll();

        //then
        assertEquals(persistedMovies, foundeMovies);
    }

    @Test
    public void findMovieByIdShouldReturnOptionalWithMovie() {
        //given
        Movie persisted = new Movie();
        long testId = 1L;
        persisted.setId(testId);
        persisted.setTitle("test");
        when(movieRepository.findById(testId)).thenReturn(Optional.of(persisted));

        //when
        Optional<Movie> foundMovie = movieRepository.findById(testId);

        //then
        assertEquals(persisted, foundMovie.get());
    }

    @Test
    public void findMovieByIdShouldReturnEmptyOptional() {
        //given
        long testId = 1L;

        //when
        Optional<Movie> foundMovie = movieRepository.findById(testId);

        //then
        assertEquals(Optional.empty(), foundMovie);
    }

    @Test
    public void saveShouldReturnSavedMovie() {
        //given
        Movie movie = new Movie();
        movie.setTitle("testTitle");
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        //when
        Movie saved = movieRepository.save(movie);

        //then
        assertEquals(movie, saved);
    }

    @Test
    public void saveShouldReturnNull() {
        //given

        //when
        Movie saved = movieRepository.save(null);

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