package pl.yahoo.pawelpiedel.Movies.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.dto.MovieDTO;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.yahoo.pawelpiedel.Movies.TestUtils.createMovieDTOFromEntity;
import static pl.yahoo.pawelpiedel.Movies.TestUtils.createTestMovieWithAllFields;
import static pl.yahoo.pawelpiedel.Movies.controller.MoviesControllerTest.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "src/test/resources/application.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RandomPortMoviesTestClient {
    private static final Logger logger = LoggerFactory.getLogger(RandomPortMoviesTestClient.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private static final String API_BASE_URL = "/api/movies";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenMoviesSavedGetAllMoviesShouldResponseWithList() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO firstMovieDTO = createMovieDTOFromEntity(movie);
        MovieDTO secondMovieDTO = createMovieDTOFromEntity(movie);
        secondMovieDTO.setTitle("Second title");

        mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(firstMovieDTO)));
        mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(secondMovieDTO)));

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(firstMovieDTO.getTitle())))
                .andExpect(jsonPath("$[0].backdropPath", is(firstMovieDTO.getBackdropPath())))
                .andExpect(jsonPath("$[0].duration", is(firstMovieDTO.getDuration())))
                .andExpect(jsonPath("$[0].budget", is(firstMovieDTO.getBudget())))
                .andExpect(jsonPath("$[0].overview", is(firstMovieDTO.getOverview())))
                .andExpect(jsonPath("$[0].date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$[0].genres[0].name", is(movie.getGenres().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCountries[0].name", is(firstMovieDTO.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$[0].productionCompanies[0].name", is(firstMovieDTO.getProductionCompanies().get(0).getName())))
                .andExpect(jsonPath("$[1].title", is(secondMovieDTO.getTitle())))
                .andExpect(jsonPath("$[1].backdropPath", is(secondMovieDTO.getBackdropPath())))
                .andExpect(jsonPath("$[1].duration", is(secondMovieDTO.getDuration())))
                .andExpect(jsonPath("$[1].budget", is(secondMovieDTO.getBudget())))
                .andExpect(jsonPath("$[1].overview", is(secondMovieDTO.getOverview())))
                .andExpect(jsonPath("$[1].date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$[1].genres[0].name", is(secondMovieDTO.getGenres().get(0).getName())))
                .andExpect(jsonPath("$[1].productionCountries[0].name", is(secondMovieDTO.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$[1].productionCompanies[0].name", is(secondMovieDTO.getProductionCompanies().get(0).getName())));
    }

    @Test
    public void whenNoMoviesSavedGetAllMoviesShouldResponseWithEmptyList() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void addMovieShouldResponseWithIsCreated() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);

        //when
        ResultActions postActions = mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)));

        //then
        postActions
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost" + API_BASE_URL)));
    }

    @Test
    public void afterAddingMovieCanBeRead() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO savedDTO = createMovieDTOFromEntity(movie);
        mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(savedDTO)));

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(savedDTO.getTitle())))
                .andExpect(jsonPath("$.backdropPath", is(savedDTO.getBackdropPath())))
                .andExpect(jsonPath("$.duration", is(savedDTO.getDuration())))
                .andExpect(jsonPath("$.budget", is(savedDTO.getBudget())))
                .andExpect(jsonPath("$.overview", is(savedDTO.getOverview())))
                .andExpect(jsonPath("$.genres[0].name", is(savedDTO.getGenres().get(0).getName())))
                .andExpect(jsonPath("$.date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$.productionCountries[0].name", is(savedDTO.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$.productionCompanies[0].name", is(savedDTO.getProductionCompanies().get(0).getName())));
    }

    @Test
    public void whenMovieWithNoTitleToCreationResponseWithClientError() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);
        movieDTO.setTitle("");

        //when
        ResultActions postActions = mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)));

        //then
        postActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenMovieWithNullTitleToCreationResponseWithClientError() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);
        movieDTO.setTitle(null);

        //when
        ResultActions postActions = mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)));

        //then
        postActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenMovieAlreadySavedWantedResponseWithDetails() throws Exception {
        //given
        Movie movie = createTestMovieWithAllFields();
        MovieDTO movieDTO = createMovieDTOFromEntity(movie);
        mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)));

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movieDTO.getTitle())))
                .andExpect(jsonPath("$.backdropPath", is(movieDTO.getBackdropPath())))
                .andExpect(jsonPath("$.duration", is(movieDTO.getDuration())))
                .andExpect(jsonPath("$.budget", is(movieDTO.getBudget())))
                .andExpect(jsonPath("$.overview", is(movieDTO.getOverview())))
                .andExpect(jsonPath("$.genres[0].name", is(movieDTO.getGenres().get(0).getName())))
                .andExpect(jsonPath("$.date", is(DATE_TIME_FORMATTER.format(movie.getReleaseDate()))))
                .andExpect(jsonPath("$.productionCountries[0].name", is(movieDTO.getProductionCountries().get(0).getName())))
                .andExpect(jsonPath("$.productionCompanies[0].name", is(movieDTO.getProductionCompanies().get(0).getName())));

    }

    @Test
    public void whenNotExistingMovieWantedResponseWithClientError() throws Exception {
        //given
        Long notExistingId = 999L;

        //when
        ResultActions resultActions = mockMvc.perform(get(API_BASE_URL + "/" + notExistingId).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().is4xxClientError());
    }
}
