package pl.yahoo.pawelpiedel.Movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
