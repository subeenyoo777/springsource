package com.example.movie.repository.total;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface MovieImageReviewRepository {

    Page<Object[]> getTotalList(String type, String Keyword, Pageable pageable);

    List<Object[]> getMovieRow(Long mno);

}
