package com.example.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.entity.Book;
import com.example.rest.repository.BookRepository;

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

    public PageResultDTO<BookDTO> readAll(PageRequestDTO pageRequestDTO) {
        // List<Book> list = bookRepository.findAll();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("code").descending());

        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredictate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()),
                        pageable);

        List<BookDTO> books = result.get().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResultDTO.<BookDTO>withAll().dtoList(books).totalCount(totalCount).pageRequestDTO(pageRequestDTO)
                .build();

        // entity => dto
        // // return modelMapper.map(book, bookDTO.class); 하나일 때는!
        // List<BookDTO> books = list.stream()
        // .map(book -> modelMapper.map(book, BookDTO.class))
        // .collect(Collectors.toList());
        // return books;
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
