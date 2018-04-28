package pl.yahoo.pawelpiedel.Movies.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.controller.MoviesController;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class EntityDTOMapper {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private ModelMapper modelMapper;

    @Autowired
    public EntityDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Movie convertToEntity(MovieDTO movieDto) {
        Movie movie = modelMapper.map(movieDto, Movie.class);
        movie.setReleaseDate(LocalDate.parse(movieDto.getDate()));

        return movie;
    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));

        return movieDTO;
    }
}
