package pl.yahoo.pawelpiedel.Movies.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class GenreDTO {
    @NotNull
    private String name;
}
