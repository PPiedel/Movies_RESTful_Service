package pl.yahoo.pawelpiedel.Movies.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


public class MovieCreationDTO {
    @NotNull
    private String title;

    private List<GenreDTO> genres;

    private List<ProductionCompanyDTO> productionCompanies;

    private List<ProductionCountryDTO> productionCountries;

    private Date releaseDate;

    private String backdropPath;

    private Integer budget;

    private String duration;

    private String overview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<ProductionCompanyDTO> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompanyDTO> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountryDTO> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountryDTO> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "MovieCreationDTO{" +
                "title='" + title + '\'' +
                ", genres=" + genres +
                ", productionCompanies=" + productionCompanies.stream().map(Object::toString) +
                ", productionCountries=" + productionCountries.stream().map(Object::toString) +
                ", releaseDate=" + releaseDate +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", duration='" + duration + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
