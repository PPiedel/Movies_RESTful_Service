package pl.yahoo.pawelpiedel.Movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.yahoo.pawelpiedel.Movies.TestUtils;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.dto.EntityDTOMapper;
import pl.yahoo.pawelpiedel.Movies.dto.MovieDTO;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.time.format.DateTimeFormatter;
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
import static pl.yahoo.pawelpiedel.Movies.TestUtils.createMovieDTOFromEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoviesControllerTest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private static final String API_BASE_URL = "/api/movies";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @Test
    public void getAllMovies_OneMovieInDB_JsonArrayReturned() throws Exception {
        //given
        Movie movie = TestUtils.createTestMovieWithAllFields();
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
    public void getAllMovies_NoMoviesInDb_EmptyJsonArrayReturned() throws Exception {
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
    public void getMovieDetails_MovieInDb_DetailsReturned() throws Exception {
        //given
        Movie movie = TestUtils.createTestMovieWithAllFields();
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
    public void getMovieDetails_NotExistingMovie_ClientErrorReturned() throws Exception {
        //given
        Long notExistingId = 999L;
        when(movieService.findMovieById(notExistingId)).thenReturn(Optional.empty());

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + notExistingId).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addMovie_ValidDTO_CreatedReturned() throws Exception {
        //given
        Movie movie = TestUtils.createTestMovieWithAllFields();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);
        when(movieService.save(ArgumentMatchers.any())).thenReturn(movie);

        //when
        ResultActions resultActions = mockMvc.perform(post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(movieDTO)));


        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost" + API_BASE_URL)));

    }

    @Test
    public void addMovie_NullDtoPassed_ClientErrorReturned() throws Exception {
        //given
        MovieDTO movieDTO = null;

        //when
        ResultActions resultActions = mockMvc.perform(post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(movieDTO)));

        //then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addMovie_EmptyDTOPassed_ClientErrorReturned() throws Exception {
        //given

        //when
        String empty = "";
        ResultActions resultActions = mockMvc.perform(post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(empty));

        //then
        resultActions
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addMovie_NotValidDtoPassed_ClientErrorReturned() throws Exception {
        //given

        //when
        String onlyNull = "null";
        ResultActions resultActions = mockMvc.perform(post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(onlyNull));

        //then
        resultActions
                .andExpect(status().is4xxClientError());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TestConfiguration
    static class MoviesControllerTestConfiguration {
        @Bean
        ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        EntityDTOMapper entityDTOMapper(ModelMapper modelMapper, GenreRepository genreRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCountryRepository productionCountryRepository) {
            return new EntityDTOMapper(modelMapper, genreRepository, productionCompanyRepository, productionCountryRepository);
        }
    }
}