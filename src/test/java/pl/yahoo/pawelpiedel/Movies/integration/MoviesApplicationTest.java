package pl.yahoo.pawelpiedel.Movies.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.yahoo.pawelpiedel.Movies.controller.MoviesController;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MoviesApplicationTest {

    @Autowired
    MoviesController moviesController;

    @Test
    public void main() {
        assertNotNull(moviesController);
    }
}