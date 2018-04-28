package pl.yahoo.pawelpiedel.Movies.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pl.yahoo.pawelpiedel.Movies.TestUtils;
import pl.yahoo.pawelpiedel.Movies.domain.Genre;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCompany;
import pl.yahoo.pawelpiedel.Movies.domain.ProductionCountry;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class EntityDTOMapperTest {
    @Autowired
    EntityDTOMapper mapper;

    @Test
    public void convertToMovieEntity() throws ParseException {
        //given
        MovieDTO movieDTO = new MovieDTO();
        GenreDTO genreDTO = new GenreDTO("comedy");
        List<GenreDTO> genres = new ArrayList<>();
        genres.add(genreDTO);

        ProductionCompanyDTO productionCompanyDTO = new ProductionCompanyDTO("Walt Disney");
        List<ProductionCompanyDTO> productionCompanies = new ArrayList<>();
        productionCompanies.add(productionCompanyDTO);


        ProductionCountryDTO productionCountryDTO = new ProductionCountryDTO("USA");
        List<ProductionCountryDTO> productionCountries = new ArrayList<>();
        productionCountries.add(productionCountryDTO);

        String testTitle = "Test title";
        movieDTO.setTitle(testTitle);
        movieDTO.setGenres(genres);
        movieDTO.setProductionCompanies(productionCompanies);
        movieDTO.setProductionCountries(productionCountries);
        int budget = 10000000;
        movieDTO.setBudget(budget);
        movieDTO.setDuration(100);
        movieDTO.setOverview("test overview");
        String date = "1990-04-21";
        LocalDate localDate = LocalDate.parse(date);
        movieDTO.setDate(date);

        //when
        Movie entity = mapper.convertToEntity(movieDTO);

        //then
        assertEquals(movieDTO.getTitle(), entity.getTitle());
        assertEquals(movieDTO.getBackdropPath(), entity.getBackdropPath());
        assertEquals(movieDTO.getBudget(), entity.getBudget());
        assertEquals(movieDTO.getDuration(), entity.getDuration());
        assertEquals(movieDTO.getOverview(), entity.getOverview());
        assertEquals(localDate,entity.getReleaseDate());
        assertEquals(movieDTO.getGenres().stream().map(GenreDTO::getName).collect(Collectors.toList()),
                entity.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));

        assertEquals(movieDTO.getProductionCompanies().stream().map(ProductionCompanyDTO::getName).collect(Collectors.toList()),
                entity.getProductionCompanies().stream().map(ProductionCompany::getName).collect(Collectors.toList()));

        assertEquals(movieDTO.getProductionCountries().stream().map(ProductionCountryDTO::getName).collect(Collectors.toList()),
                entity.getProductionCountries().stream().map(ProductionCountry::getName).collect(Collectors.toList()));
    }

    @Test
    public void convertToDTO() throws Exception {
        //given
        Movie entity = TestUtils.createTestMovieWithAllFields();

        //when
        MovieDTO convertedDTO = mapper.convertToDTO(entity);

        //then
        assertEquals(entity.getTitle(), convertedDTO.getTitle());
        assertEquals(entity.getBackdropPath(), convertedDTO.getBackdropPath());
        assertEquals(entity.getBudget(), convertedDTO.getBudget());
        assertEquals(entity.getDuration(), convertedDTO.getDuration());
        assertEquals(entity.getOverview(), convertedDTO.getOverview());
        assertEquals(entity.getReleaseDate(), LocalDate.parse(convertedDTO.getDate()));
        assertEquals(entity.getGenres().stream().map(Genre::getName).collect(Collectors.toList()),
                convertedDTO.getGenres().stream().map(GenreDTO::getName).collect(Collectors.toList()));

        assertEquals(entity.getProductionCompanies().stream().map(ProductionCompany::getName).collect(Collectors.toList()),
                convertedDTO.getProductionCompanies().stream().map(ProductionCompanyDTO::getName).collect(Collectors.toList()));

        assertEquals(entity.getProductionCountries().stream().map(ProductionCountry::getName).collect(Collectors.toList()),
                convertedDTO.getProductionCountries().stream().map(ProductionCountryDTO::getName).collect(Collectors.toList()));
    }

    @TestConfiguration
    static class EntityDTOMapperTestConfiguration {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public EntityDTOMapper entityDTOMapper(ModelMapper modelMapper) {
            return new EntityDTOMapper(modelMapper);
        }
    }
}