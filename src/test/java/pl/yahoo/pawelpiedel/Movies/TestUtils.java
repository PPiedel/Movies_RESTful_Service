package pl.yahoo.pawelpiedel.Movies;

import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.dto.GenreDTO;
import pl.yahoo.pawelpiedel.Movies.dto.MovieDTO;
import pl.yahoo.pawelpiedel.Movies.dto.ProductionCompanyDTO;
import pl.yahoo.pawelpiedel.Movies.dto.ProductionCountryDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");

    public static Movie createTestMovieWithAllFields() {
        Genre genre = new Genre();
        genre.setName("comedy");
        List<Genre> genres = Collections.singletonList(genre);

        ProductionCountry productionCountry = new ProductionCountry();
        productionCountry.setId(1L);
        productionCountry.setName("Poland");
        List<ProductionCountry> productionCountries = Collections.singletonList(productionCountry);

        ProductionCompany productionCompany = new ProductionCompany();
        productionCompany.setId(1L);
        productionCompany.setName("Walt Disney");
        List<ProductionCompany> productionCompanies = Collections.singletonList(productionCompany);

        Movie movie = new Movie();
        String movie1Title = "test1";
        movie.setId(1L);
        movie.setTitle(movie1Title);
        movie.setGenres(genres);
        movie.setProductionCountries(productionCountries);
        movie.setProductionCompanies(productionCompanies);
        movie.setBudget(10000000);
        movie.setDuration(123);
        String backdropPath = "http://www.image1.pl/i1";
        movie.setBackdropPath(backdropPath);
        String testOverview = "test Overview";
        movie.setOverview(testOverview);
        movie.setReleaseDate(LocalDate.of(1990, 2, 24));
        return movie;
    }

    public static MovieDTO createMovieDTOFromEntity(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();

        List<GenreDTO> genres = movie.getGenres().stream()
                .map(genre -> new GenreDTO(genre.getName()))
                .collect(Collectors.toList());
        List<ProductionCompanyDTO> productionCompanies = movie.getProductionCompanies().stream()
                .map(productionCompany -> new ProductionCompanyDTO(productionCompany.getName()))
                .collect(Collectors.toList());
        List<ProductionCountryDTO> productionCountries = movie.getProductionCountries().stream()
                .map(productionCountry -> new ProductionCountryDTO(productionCountry.getName()))
                .collect(Collectors.toList());

        movieDTO.setTitle(movie.getTitle());
        movieDTO.setGenres(genres);
        movieDTO.setProductionCompanies(productionCompanies);
        movieDTO.setProductionCountries(productionCountries);
        movieDTO.setBudget(movie.getBudget());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));

        return movieDTO;
    }
}
