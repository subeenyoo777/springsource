package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.service.BookService;

// 일반 컨트롤럴 + REST
// 데이터 내보내기 : ResponseEntity or @ResponseBody
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@lombok.extern.log4j.Log4j2

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService; // 서비스 이용하기 위함

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("book") BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서 작성 요청");
    }

    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO dto) {
        log.info("book list 요청");

        // 서비스 호출
        Long code = bookService.insert(dto);
        return code;
    }

    // http://localhost:8080/book/list?page=1&size=10
    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book list 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        // model.addAttribute("result", pageResultDTO);

        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify" })
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("book get 요청 {}", code);
        BookDTO book = bookService.read(code);
        return book;
    }

    @ResponseBody
    @GetMapping({ "/read", "/modify" })
    public BookDTO getRead(PageRequestDTO pageRequestDTO, Long code, Model model) {
        log.info("book get 요청 {}", code);

        BookDTO dto = bookService.read(code);
        // model.addAttribute("book", book); // book 밑 read 파악.
        return dto;
    }

    @ResponseBody
    @PutMapping("/modify")
    public Long postModify(@RequestBody BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("book modify 요청 {}", dto);

        // service 호출
        bookService.modify(dto);

        return dto.getCode();
    }

    @ResponseBody
    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> postRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("book remove 요청 {}", code);

        // 서비스 호출
        bookService.remove(code);

        bookService.remove(code);
        return new ResponseEntity<Long>(code, HttpStatus.OK);
    }

}
