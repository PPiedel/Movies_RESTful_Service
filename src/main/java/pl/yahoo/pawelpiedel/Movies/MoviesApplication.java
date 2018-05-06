package pl.yahoo.pawelpiedel.Movies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
    @Profile("!test")
    public CommandLineRunner loadData(MovieRepository movieRepository) {
		return (args) -> {
            Movie movie1 = new Movie();
            movie1.setTitle("The Shawshank Redemption");
            movie1.setBudget(100000);
            movie1.setGenres(Arrays.asList(new Genre("Comedy"),new Genre("Drama"),new Genre("Action")));
            movie1.setProductionCompanies(Arrays.asList(new ProductionCompany("Walt Disney"),new ProductionCompany("Warner Bross")));
            movie1.setProductionCountries(Arrays.asList(new ProductionCountry("Poland"),new ProductionCountry("Germany"),new ProductionCountry("USA")));
            movie1.setDuration(120);
            movie1.setReleaseDate(LocalDate.now());
            movie1.setOverview("Simple movie overview");

            Movie movie2 = new Movie();
            movie2.setTitle("Pirates of the Caribbean");
            movie2.setBudget(120000000);
            movie2.setGenres(Arrays.asList(new Genre("Romance"),new Genre("Thriller")));
            movie2.setProductionCountries(Arrays.asList(new ProductionCountry("UK"),new ProductionCountry("Norway")));
            movie2.setProductionCompanies(Arrays.asList(new ProductionCompany("Movies Studio"),new ProductionCompany("Pictures Studio")));
            movie2.setDuration(100);
            movie2.setReleaseDate(LocalDate.now());
            movie2.setOverview("Pirates of the Caribbean is a great movie.");

            movieRepository.saveAll(Arrays.asList(movie1,movie2));

		};
	}

}
