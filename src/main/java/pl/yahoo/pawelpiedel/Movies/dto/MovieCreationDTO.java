package pl.yahoo.pawelpiedel.Movies.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MovieCreationDTO {
    @NotNull
    private String title;

    private List<GenreDTO> genres;

    private List<ProductionCompanyDTO> productionCompanies;

}
