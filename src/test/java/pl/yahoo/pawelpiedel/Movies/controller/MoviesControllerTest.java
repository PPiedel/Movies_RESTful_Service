package pl.yahoo.pawelpiedel.Movies.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MoviesController.class)
public class MoviesControllerTest {
    private static final String API_BASE_URL = "/api/movies";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @Test
    public void getAllMoviesShouldReturnJsonArrayWithOneMovieDTO() throws Exception {
        //given
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("comedy");
        List<Genre> genres = Collections.singletonList(genre);

        ProductionCountry productionCountry = new ProductionCountry();
        productionCountry.setId(1L);
        productionCountry.setName("Poland");
        List<ProductionCountry> productionCountries = Collections.singletonList(productionCountry);

        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setId(1L);
        productionCompany.setName("Walt Disney");
        List<ProductionCompany> productionCompanies = Collections.singletonList(productionCompany);

        Movie movie = new Movie();
        String movie1Title = "test1";
        movie.setId(1L);
        movie.setTitle(movie1Title);
        movie.setGenres(genres);
        movie.setProductionCountries(productionCountries);
        movie.setProductionCompanies(productionCompanies);
        movie.setBudget(10000000);
        movie.setDuration(123);
        String backdropPath = "http://www.image1.pl/i1";
        movie.setBackdropPath(backdropPath);
        String testOverview = "test Overview";
        movie.setOverview(testOverview);
        List<Movie> movies = Collections.singletonList(movie);

        when(movieService.getAllMovies()).thenReturn(movies);


        //then
        mockMvc.perform(get(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(movie.getTitle())))
                .andExpect(jsonPath("$[0].backdropPath", is(movie.getBackdropPath())))
                .andExpect(jsonPath("$[0].duration", is(movie.getDuration())))
                .andExpect(jsonPath("$[0].budget", is(movie.getBudget())))
                .andExpect(jsonPath("$[0].overview", is(movie.getOverview())))
                .andExpect(jsonPath("$[0].genres[0].name", is(movie.getGenres().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCountries[0].name", is(movie.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCompanies[0].name", is(movie.getProductionCompanies().get(0).getName())));
    }

    @Test
    public void getAllMoviesShouldReturnEmptyJsonArray() throws Exception {
        //given
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());

        //then
        long testId = 1L;
        mockMvc.perform(get(API_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getMovieDetails() {
    }

    @Test
    public void addMovie() {
    }

    @TestConfiguration
    static class MoviesControllerTestConfiguration {
        @Bean
        ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }
}