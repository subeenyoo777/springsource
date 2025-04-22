package com.example.mart.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Test
    public void categoryItemInsert() {
        IntStream.rangeClosed(1, 2).forEach(i -> {
            CategoryItem categoryItem = CategoryItem.builder()
                    .category(1)
                    .item("컴퓨터")
                    .build();

            categoryItemRepository.save(categoryItem);
        });
    }
}
