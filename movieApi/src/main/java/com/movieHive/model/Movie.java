package com.movieHive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "please provide movie's title !")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie's director !")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie's studio !")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie's release year !")
    private String releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "please provide movie's poster !")
    private String poster;
}
