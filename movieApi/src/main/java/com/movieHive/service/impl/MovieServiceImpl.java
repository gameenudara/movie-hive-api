package com.movieHive.service.impl;

import com.movieHive.DTO.MovieDTO;
import com.movieHive.model.Movie;
import com.movieHive.repository.MovieRepository;
import com.movieHive.service.FileService;
import com.movieHive.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) throws IOException {
        String uploadFileName = fileService.uploadFile(path, file);
        movieDTO.setPoster(uploadFileName);

        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setStudio(movieDTO.getStudio());
        movie.setMovieCast(movieDTO.getMovieCast());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setPoster(movieDTO.getPoster());

        Movie savedMovie = movieRepository.save(movie);
        String posterUrl = "/file/" + uploadFileName;

        MovieDTO response = new MovieDTO(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public MovieDTO getMovie(Long id) {

        Movie movie = movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found"));

        String PosterUrl = path +"/file/" + movie.getPoster();

            MovieDTO response = new MovieDTO(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    PosterUrl
            );

        return response;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (Movie movie : movies) {
            String PosterUrl = path +"/file/" + movie.getPoster();
            MovieDTO response = new MovieDTO(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    PosterUrl
            );
            movieDTOList.add(response);
        }
        return movieDTOList;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO, MultipartFile file) throws IOException {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found"));
        String fileName = movie.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }
        movieDTO.setPoster(fileName);

        String PosterUrl = path +"/file/" + fileName;

        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setStudio(movieDTO.getStudio());
        movie.setMovieCast(movieDTO.getMovieCast());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setPoster(PosterUrl);
        movieRepository.save(movie);

        return movieDTO;
    }

    @Override
    public String deleteMovie(Long id) throws IOException{
        Movie movie = movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found"));

        Long movieId = movie.getId();
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));
        movieRepository.delete(movie);

        return "Movie deleted with id: " + movieId;
    }
}

