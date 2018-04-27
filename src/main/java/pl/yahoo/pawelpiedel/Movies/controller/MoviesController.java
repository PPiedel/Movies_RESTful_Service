package pl.yahoo.pawelpiedel.Movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.dto.EntityDTOMapper;
import pl.yahoo.pawelpiedel.Movies.dto.MovieDTO;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/movies")
public class MoviesController {
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);
    private MovieService movieService;
    private EntityDTOMapper mapper;

    @Autowired
    public MoviesController(MovieService movieService, EntityDTOMapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();

        return allMovies.stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MovieDTO> getMovieDetails(@PathVariable(value = "id") Long id) {
        Optional<Movie> movie = movieService.findMovieById(id);
        return movie
                .map(value -> new ResponseEntity<>(mapper.convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO movieDTO) {
        Movie movie;
        movie = mapper.convertToEntity(movieDTO);
        logger.info(movie.toString());

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
