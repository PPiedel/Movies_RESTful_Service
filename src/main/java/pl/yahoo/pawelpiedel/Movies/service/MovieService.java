package pl.yahoo.pawelpiedel.Movies.service;

import java.util.List;

import org.springframework.stereotype.Service;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;

@Service
public interface MovieService {
    List<Movie> getAllMovies();
}
