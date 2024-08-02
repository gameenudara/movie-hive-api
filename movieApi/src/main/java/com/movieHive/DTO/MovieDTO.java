package com.movieHive.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long id;

    @NotBlank(message = "please provide movie's title !")
    private String title;

    @NotBlank(message = "please provide movie's director !")
    private String director;

    @NotBlank(message = "please provide movie's studio !")
    private String studio;

    private Set<String> movieCast;

    @NotBlank(message = "please provide movie's releaseYear !")
    private String releaseYear;

    @NotBlank(message = "please provide movie's poster !")
    private String poster;

    @NotBlank(message = "please provide movie's poster url !")
    private String posterUrl;

}
