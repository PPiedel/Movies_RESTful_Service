package pl.yahoo.pawelpiedel.Movies.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movies_production_companies",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "production_company_id", referencedColumnName = "id"))
    private List<ProductionCompany> productionCompanies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movies_production_countries",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "production_country_id", referencedColumnName = "id"))
    private List<ProductionCountry> productionCountries = new ArrayList<>();

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "backdrop_path")
    private String backdropPath;

    private Integer budget;

    private Integer duration;

    private String overview;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(productionCompanies, movie.productionCompanies) &&
                Objects.equals(productionCountries, movie.productionCountries) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(backdropPath, movie.backdropPath) &&
                Objects.equals(budget, movie.budget) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(overview, movie.overview);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, genres, productionCompanies, productionCountries, releaseDate, backdropPath, budget, duration, overview);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genres=" + genres.stream().map(Genre::toString) +
                ", productionCompanies=" + productionCompanies.stream().map(ProductionCompany::toString) +
                ", productionCountries=" + productionCountries.stream().map(ProductionCountry::toString) +
                ", releaseDate=" + releaseDate +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", duration='" + duration + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
