package pl.yahoo.pawelpiedel.Movies.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.dto.*;
import pl.yahoo.pawelpiedel.Movies.service.MovieService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/movies")
public class MoviesController {

    @Autowired
    MovieService movieService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();

        return allMovies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MovieDTO convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDTO.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MovieDTO> getMovieDetails(@PathVariable(value = "id") Long id) {
        Optional<Movie> movie = movieService.findMovieById(id);
        return movie
                .map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieCreationDTO movieCreationDTO) {
        Movie movie = convertToEntity(movieCreationDTO);

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

    private Movie convertToEntity(MovieCreationDTO movieCreationDTO) {
        List<Genre> genres = movieCreationDTO.getGenres().stream().map(this::convertToEntity).collect(Collectors.toList());
        List<ProductionCompany> productionCompanies = movieCreationDTO.getProductionCompanies().stream().map(this::convertToEntity).collect(Collectors.toList());
        List<ProductionCountry> productionCountries = movieCreationDTO.getProductionCountries().stream().map(this::convertToEntity).collect(Collectors.toList());

        Movie movie = modelMapper.map(movieCreationDTO, Movie.class);
        movie.setGenres(genres);
        movie.setProductionCompanies(productionCompanies);
        movie.setProductionCountries(productionCountries);

        return movie;
    }

    private Genre convertToEntity(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    private ProductionCompany convertToEntity(ProductionCompanyDTO productionCompanyDTO) {
        return modelMapper.map(productionCompanyDTO, ProductionCompany.class);
    }

    private ProductionCountry convertToEntity(ProductionCountryDTO productionCountryDTO) {
        return modelMapper.map(productionCountryDTO, ProductionCountry.class);
    }
}
