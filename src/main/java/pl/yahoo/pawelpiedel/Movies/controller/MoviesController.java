package pl.yahoo.pawelpiedel.Movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
public class MoviesController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> getMovieDetails(@PathVariable(value = "id") Long id) {
        Optional<Movie> movie = movieService.findMovieById(id);
        return movie
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){
        Movie savedMovie = movieService.save(movie);

        if (savedMovie!=null){
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedMovie.getId()).toUri();

            return ResponseEntity.created(location).build();
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
}
