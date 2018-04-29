package pl.yahoo.pawelpiedel.Movies.service.genre;

import org.springframework.stereotype.Service;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;

@Service
public interface GenreService {
    Genre findByName(String name);
}
