package pl.yahoo.pawelpiedel.Movies.dto;

public class ProductionCountryDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductionCountryDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
