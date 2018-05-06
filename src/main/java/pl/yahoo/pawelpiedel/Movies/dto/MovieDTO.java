package pl.yahoo.pawelpiedel.Movies.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import pl.yahoo.pawelpiedel.Movies.controller.MoviesController;
import pl.yahoo.pawelpiedel.Movies.domain.Movie;
import pl.yahoo.pawelpiedel.Movies.domain.date.DateConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class MovieDTO extends ResourceSupport {
    private final static String ALL_MOVIES_LINK_RELATION_NAME = "all";

    @NotNull
    @NotEmpty
    private String title;
    private List<GenreDTO> genres;
    private List<ProductionCompanyDTO> productionCompanies;
    private List<ProductionCountryDTO> productionCountries;
    @DateConstraint
    private String date;
    private String backdropPath;
    private Integer budget;
    private Integer duration;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieDTO addLinks(Movie movie) {
        addSelfLink(movie);
        addAllMoviesLink();
        return this;
    }

    private void addSelfLink(Movie movie) {
        Link selfLink = linkTo(methodOn(MoviesController.class).getMovieDetails(movie.getId())).withSelfRel();
        add(selfLink);
    }

    private void addAllMoviesLink() {
        Link allLink = linkTo(methodOn(MoviesController.class).getAllMovies()).withRel(ALL_MOVIES_LINK_RELATION_NAME);
        add(allLink);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "title='" + title + '\'' +
                ", genres=" + genres.stream().map(GenreDTO::toString).collect(Collectors.toList()) +
                ", productionCompanies=" + productionCompanies.stream().map(ProductionCompanyDTO::toString).collect(Collectors.toList()) +
                ", productionCountries=" + productionCountries.stream().map(ProductionCountryDTO::toString).collect(Collectors.toList()) +
                ", date='" + date + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", duration=" + duration +
                ", overview='" + overview + '\'' +
                '}';
    }
}
