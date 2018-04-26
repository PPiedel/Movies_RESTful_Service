package pl.yahoo.pawelpiedel.Movies.dto;

import javax.validation.constraints.NotNull;

public class ProductionCountryDTO {
    @NotNull
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
