package com.movieHive.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieHive.DTO.MovieDTO;
import com.movieHive.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    private MovieDTO convertToJson(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDTO.class);
    }
}

