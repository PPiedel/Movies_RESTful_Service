package pl.yahoo.pawelpiedel.Movies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;
import pl.yahoo.pawelpiedel.Movies.repository.GenreRepository;
import pl.yahoo.pawelpiedel.Movies.repository.MovieRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCompanyRepository;
import pl.yahoo.pawelpiedel.Movies.repository.ProductionCountryRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Bean
    @Transactional
    public CommandLineRunner loadData(GenreRepository genreRepository, ProductionCountryRepository productionCountryRepository,
                                      ProductionCompanyRepository productionCompanyRepository, MovieRepository movieRepository) {
		return (args) -> {
            ProductionCountry productionCountry4 = productionCountryRepository.saveAndFlush(new ProductionCountry("UK"));
            ProductionCountry productionCountry5 = productionCountryRepository.saveAndFlush(new ProductionCountry("Norway"));

            ProductionCompany productionCompany3 = productionCompanyRepository.saveAndFlush(new ProductionCompany("Movies Studio"));
            ProductionCompany productionCompany4 = productionCompanyRepository.saveAndFlush(new ProductionCompany("Pictures Studio"));

            Movie movie1 = new Movie();
            movie1.setTitle("The Shawshank Redemption");
            movie1.setBudget(100000);
            movie1.setGenres(Arrays.asList(new Genre("Comedy"),new Genre("Drama"),new Genre("Action")));
            movie1.setProductionCompanies(Arrays.asList(new ProductionCompany("Walt Disney"),new ProductionCompany("Warner Bross")));
            movie1.setProductionCountries(Arrays.asList(new ProductionCountry("Poland"),new ProductionCountry("Germany"),new ProductionCountry("USA")));
            movie1.setDuration(120);
            movie1.setReleaseDate(LocalDate.now());
            movie1.setOverview("Simple movie overview");
            movieRepository.save(movie1);


		};
	}

}
