package pl.yahoo.pawelpiedel.Movies.dto;


public class GenreDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
