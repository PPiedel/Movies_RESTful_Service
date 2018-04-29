package pl.yahoo.pawelpiedel.Movies.service.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;

@Service
public interface MovieService {
    List<Movie> getAllMovies();

    Optional<Movie> findMovieById(Long id);

    Movie save(Movie movie);
}
