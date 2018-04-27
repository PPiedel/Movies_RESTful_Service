package pl.yahoo.pawelpiedel.Movies.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.yahoo.pawelpiedel.Movies.controller.MoviesControllerTest;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.dto.MovieDTO;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.yahoo.pawelpiedel.Movies.controller.MoviesControllerTest.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "src/test/resources/application.properties")
@AutoConfigureMockMvc
public class RandomPortWebTestClient {
    private static final String API_BASE_URL = "/api/movies";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenMoviesWhenGetMoviesThenResultIsOk() {

    }

    @Test
    public void whenMovieSavedReponseIsCreated() {
        //given

        //then

    }
    
    @Test
    public void whenTwiceSavedMovieShouldBeUpdated() throws Exception {
        //given
        Movie movie = MoviesControllerTest.createTestMovie();
        MovieDTO movieDTO = MoviesControllerTest.createMovieDTOFromEntity(movie);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(movieDTO)));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost" + API_BASE_URL)));

    }
}
