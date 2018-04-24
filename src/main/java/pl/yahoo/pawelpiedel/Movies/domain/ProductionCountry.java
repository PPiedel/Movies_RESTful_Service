package pl.yahoo.pawelpiedel.Movies.domain;

import java.util.Objects;

public class ProductionCountry {
    private String name;

    private String isoCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionCountry that = (ProductionCountry) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(isoCode, that.isoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isoCode);
    }

    @Override
    public String toString() {
        return "ProductionCountry{" +
                "name='" + name + '\'' +
                ", isoCode='" + isoCode + '\'' +
                '}';
    }
}
