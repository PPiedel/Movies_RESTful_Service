package pl.yahoo.pawelpiedel.Movies.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "production_country")
public class ProductionCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "iso_code")
    private String isoCode;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
