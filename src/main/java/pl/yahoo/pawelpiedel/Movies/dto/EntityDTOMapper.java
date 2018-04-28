package pl.yahoo.pawelpiedel.Movies.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.controller.MoviesController;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class EntityDTOMapper {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private final static String ALL_MOVIES_LINK_RELATION_NAME = "all";
    private ModelMapper modelMapper;

    @Autowired
    public EntityDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Movie convertToEntity(MovieDTO movieDto) {
        List<Genre> genres = movieDto.getGenres().stream().map(this::convertToEntity).collect(Collectors.toList());
        List<ProductionCompany> productionCompanies = movieDto.getProductionCompanies().stream().map(this::convertToEntity).collect(Collectors.toList());
        List<ProductionCountry> productionCountries = movieDto.getProductionCountries().stream().map(this::convertToEntity).collect(Collectors.toList());

        Movie movie = modelMapper.map(movieDto, Movie.class);
        movie.setGenres(genres);
        movie.setProductionCompanies(productionCompanies);
        movie.setProductionCountries(productionCountries);
        movie.setReleaseDate(LocalDate.parse(movieDto.getDate()));

        return movie;
    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));

        addSelfLink(movie, movieDTO);
        addAllMoviesLink(movieDTO);

        return movieDTO;
    }

    private void addSelfLink(Movie movie, MovieDTO movieDTO) {
        Link selfLink = linkTo(methodOn(MoviesController.class).getMovieDetails(movie.getId())).withSelfRel();
        movieDTO.add(selfLink);
    }

    private void addAllMoviesLink(MovieDTO movieDTO) {
        Link allLink = linkTo(methodOn(MoviesController.class).getAllMovies()).withRel(ALL_MOVIES_LINK_RELATION_NAME);
        movieDTO.add(allLink);
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
