package pl.yahoo.pawelpiedel.Movies.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EntityDTOMapper {
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProductionCountryRepository productionCountryRepository;

    @Autowired
    public EntityDTOMapper(ModelMapper modelMapper, GenreRepository genreRepository, ProductionCompanyRepository productionCompanyRepository, ProductionCountryRepository productionCountryRepository) {
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.productionCountryRepository = productionCountryRepository;
    }

    public Movie asEntity(MovieDTO movieDto) {
        return createMovieToSave(movieDto);
    }

    private Genre asEntity(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    private ProductionCompany asEntity(ProductionCompanyDTO productionCompanyDTO) {
        return modelMapper.map(productionCompanyDTO, ProductionCompany.class);
    }

    private ProductionCountry asEntity(ProductionCountryDTO productionCountryDTO) {
        return modelMapper.map(productionCountryDTO, ProductionCountry.class);
    }

    public MovieDTO asDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDate(movie.getReleaseDate().format(DATE_TIME_FORMATTER));

        return movieDTO;
    }

    private Movie createMovieToSave(MovieDTO movieDTO) {
        Movie toSave = new Movie();
        toSave.setTitle(movieDTO.getTitle());
        toSave.setOverview(movieDTO.getOverview());
        toSave.setBackdropPath(movieDTO.getBackdropPath());
        toSave.setReleaseDate(LocalDate.parse(movieDTO.getDate()));
        toSave.setBudget(movieDTO.getBudget());
        toSave.setDuration(movieDTO.getDuration());

        if (movieDTO.getGenres() != null) {
            movieDTO.getGenres().forEach(genreDTO -> {
                toSave.getGenres().add(persistGenre(asEntity(genreDTO)));
            });
        }

        if (movieDTO.getProductionCompanies() != null) {
            movieDTO.getProductionCompanies().forEach(productionCompanyDTO -> {
                toSave.getProductionCompanies().add(persistProductionCompany(asEntity(productionCompanyDTO)));
            });
        }

        if (movieDTO.getProductionCountries() != null) {
            movieDTO.getProductionCountries().forEach(productionCountryDTO -> {
                toSave.getProductionCountries().add(persistProductionCountry(asEntity(productionCountryDTO)));
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
