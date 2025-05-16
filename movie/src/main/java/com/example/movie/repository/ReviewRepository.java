package com.example.movie.repository;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);
}
