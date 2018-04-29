package pl.yahoo.pawelpiedel.Movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;
import pl.yahoo.pawelpiedel.Movies.repository.MovieRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProductionCountryRepository productionCountryRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCountryRepository productionCountryRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCountryRepository = productionCountryRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie save(Movie movie) {
        Movie toSave = createMovieToSave(movie);
        return movieRepository.save(toSave);
    }

    private Movie createMovieToSave(Movie movie) {
        Movie toSave = new Movie();
        toSave.setTitle(movie.getTitle());
        toSave.setOverview(movie.getOverview());
        toSave.setBackdropPath(movie.getBackdropPath());
        toSave.setReleaseDate(movie.getReleaseDate());
        toSave.setBudget(movie.getBudget());
        toSave.setDuration(movie.getDuration());

        if (movie.getGenres() != null) {
            movie.getGenres().forEach(genre -> {
                toSave.getGenres().add(persistGenre(genre));
            });
        }

        if (movie.getProductionCompanies() != null) {
            movie.getProductionCompanies().forEach(productionCompany -> {
                toSave.getProductionCompanies().add(persistProductionCompany(productionCompany));
            });
        }

        if (movie.getProductionCountries() != null) {
            movie.getProductionCountries().forEach(productionCountry -> {
                toSave.getProductionCountries().add(persistProductionCountry(productionCountry));
            });
        }
        return toSave;
    }

    private ProductionCountry persistProductionCountry(ProductionCountry productionCountry) {
        ProductionCountry persisted = productionCountryRepository.findByName(productionCountry.getName());
        if (persisted == null) {
            persisted = productionCountryRepository.save(productionCountry);
        }
        return persisted;
    }

    private ProductionCompany persistProductionCompany(ProductionCompany productionCompany) {
        ProductionCompany persisted = productionCompanyRepository.findByName(productionCompany.getName());
        if (persisted == null) {
            persisted = productionCompanyRepository.save(productionCompany);
        }
        return persisted;
    }

    private Genre persistGenre(Genre genre) {
        Genre persisted = genreRepository.findByName(genre.getName());
        if (persisted == null) {
            persisted = genreRepository.save(genre);
        }
        return persisted;
    }


}
