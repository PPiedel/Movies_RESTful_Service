package pl.yahoo.pawelpiedel.Movies.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "Movies_Genres",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(name = "Movies_Production_Companies",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "production_company_id", referencedColumnName = "id"))
    private List<ProductionCompany> productionCompanies;

    @ManyToMany
    @JoinTable(name = "Movies_Production_Companies",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "production_country_id", referencedColumnName = "id"))
    private List<ProductionCountry> productionCountries;
    private String title;
    private int budget;
    private Date releaseDate;
    private String backdropPath;
    private String duration;
    private String overview;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return budget == movie.budget &&
                Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(productionCompanies, movie.productionCompanies) &&
                Objects.equals(productionCountries, movie.productionCountries) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(backdropPath, movie.backdropPath) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(overview, movie.overview);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, genres, productionCompanies, productionCountries, budget, releaseDate, backdropPath, duration, overview);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", budget=" + budget +
                ", releaseDate=" + releaseDate +
                ", backdropPath='" + backdropPath + '\'' +
                ", duration='" + duration + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
