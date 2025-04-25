package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        // dto => entity(insert 작업했던 걸 modelmapper로 변환해달라)
        // Book book = Book.builder().title(dto.getTitle())~~.build() X

        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();

    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> readAll() {
        List<Book> list = bookRepository.findAll();
        // entity => dto
        // return modelMapper.map(book, bookDTO.class); 하나일 때는!

        List<BookDTO> books = list.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return books;
    }

    public void modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setPrice(dto.getPrice());
        bookRepository.save(book);
    }

    public void remove(Long code) {
        // repository 호출!
        bookRepository.deleteById(code);
    }
}
