package com.movieHive.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieHive.DTO.MovieDTO;
import com.movieHive.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieDTO> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDTO) throws IOException {
        MovieDTO dto = convertToJson(movieDTO);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieHandler(@PathVariable Long id) {
        return ResponseEntity.ok( movieService.getMovie(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovieHandler() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    private MovieDTO convertToJson(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDTO.class);
    }
}

