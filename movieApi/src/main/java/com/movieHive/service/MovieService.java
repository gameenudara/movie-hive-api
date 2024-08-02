package com.movieHive.service;

import com.movieHive.DTO.MovieDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface MovieService {
    MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException;

    MovieDTO getMovie(Long id);

    List<MovieDTO> getAllMovies();
}
