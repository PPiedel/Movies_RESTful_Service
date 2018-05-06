package pl.yahoo.pawelpiedel.Movies.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductionCountryDTO {
    @NotNull
    @NotEmpty
    private String name;

    public ProductionCountryDTO() {
    }

    public ProductionCountryDTO(@NotNull String name) {
        this.name = name;
    }

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
