package pl.yahoo.pawelpiedel.Movies.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDTOMapper {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
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

        System.out.println(movieDto.getDate());

        movie.setReleaseDate(LocalDate.parse(movieDto.getDate()));

        return movie;
    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));
        System.out.println(movieDTO.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public Genre convertToEntity(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    public ProductionCompany convertToEntity(ProductionCompanyDTO productionCompanyDTO) {
        return modelMapper.map(productionCompanyDTO, ProductionCompany.class);
    }

    public ProductionCountry convertToEntity(ProductionCountryDTO productionCountryDTO) {
        return modelMapper.map(productionCountryDTO, ProductionCountry.class);
    }
}
