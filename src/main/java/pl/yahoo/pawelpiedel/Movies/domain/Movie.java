package pl.yahoo.pawelpiedel.Movies.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Movie {
    private Long id;
    private String title;
    private List<Genre> genres;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
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
