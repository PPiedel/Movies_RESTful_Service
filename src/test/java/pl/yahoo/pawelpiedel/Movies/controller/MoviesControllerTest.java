package pl.yahoo.pawelpiedel.Movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.dto.*;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.yahoo.pawelpiedel.Movies.dto.EntityDTOMapper.DATE_TIME_FORMATTER;

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
        Movie movie = createTestMovie();
        List<Movie> movies = Collections.singletonList(movie);
        when(movieService.getAllMovies()).thenReturn(movies);

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(movie.getTitle())))
                .andExpect(jsonPath("$[0].backdropPath", is(movie.getBackdropPath())))
                .andExpect(jsonPath("$[0].duration", is(movie.getDuration())))
                .andExpect(jsonPath("$[0].budget", is(movie.getBudget())))
                .andExpect(jsonPath("$[0].overview", is(movie.getOverview())))
                .andExpect(jsonPath("$[0].date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$[0].genres[0].name", is(movie.getGenres().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCountries[0].name", is(movie.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCompanies[0].name", is(movie.getProductionCompanies().get(0).getName())));
    }

    @Test
    public void getAllMoviesShouldReturnEmptyJsonArray() throws Exception {
        //given
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getMovieDetailsShouldReturnOkResponseWithMovie() throws Exception {
        //given
        Movie movie = createTestMovie();
        movie.setId(1L);
        when(movieService.findMovieById(movie.getId())).thenReturn(Optional.of(movie));

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + movie.getId()).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.backdropPath", is(movie.getBackdropPath())))
                .andExpect(jsonPath("$.duration", is(movie.getDuration())))
                .andExpect(jsonPath("$.budget", is(movie.getBudget())))
                .andExpect(jsonPath("$.overview", is(movie.getOverview())))
                .andExpect(jsonPath("$.genres[0].name", is(movie.getGenres().get(0).getName())))
                .andExpect(jsonPath("$.date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$.productionCountries[0].name", is(movie.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$.productionCompanies[0].name", is(movie.getProductionCompanies().get(0).getName())));
    }

    @Test
    public void getMovieDetailsShouldReturnNotFoundResponse() throws Exception {
        //given
        Long notExistingId = 5L;
        when(movieService.findMovieById(notExistingId)).thenReturn(Optional.empty());

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + notExistingId).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addMovieShouldResposneWithIsCreated() throws Exception {
        //given
        Movie movie = createTestMovie();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);
        when(movieService.save(ArgumentMatchers.any())).thenReturn(movie);

        //when
        ResultActions resultActions = mockMvc.perform(post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(movieDTO)));


        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost" + API_BASE_URL)));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO updateExistingMovie;

    //TODO tryToAddEmptyDTO;

    private Movie createTestMovie() {
        Genre genre = new Genre();
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
        movie.setReleaseDate(LocalDate.of(1990, 2, 24));
        return movie;
    }

    private MovieDTO createMovieDTOFromEntity(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        GenreDTO genreDTO1 = new GenreDTO(movie.getGenres().get(0).getName());
        List<GenreDTO> genres = new ArrayList<>();
        genres.add(genreDTO1);

        ProductionCompanyDTO productionCompanyDTO1 = new ProductionCompanyDTO(movie.getProductionCompanies().get(0).getName());
        List<ProductionCompanyDTO> productionCompanies = new ArrayList<>();
        productionCompanies.add(productionCompanyDTO1);


        ProductionCountryDTO productionCountryDTO1 = new ProductionCountryDTO(movie.getProductionCountries().get(0).getName());
        List<ProductionCountryDTO> productionCountries = new ArrayList<>();
        productionCountries.add(productionCountryDTO1);

        movieDTO.setTitle(movie.getTitle());
        movieDTO.setGenres(genres);
        movieDTO.setProductionCompanies(productionCompanies);
        movieDTO.setProductionCountries(productionCountries);
        movieDTO.setBudget(movie.getBudget());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));

        return movieDTO;
    }

    @TestConfiguration
    static class MoviesControllerTestConfiguration {
        @Bean
        ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        EntityDTOMapper entityDTOMapper(ModelMapper modelMapper) {
            return new EntityDTOMapper(modelMapper);
        }
    }
}