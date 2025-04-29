package com.example.book.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/create")
    public String postCreate(@ModelAttribute("book") @Valid BookDTO dto, BindingResult result,
            RedirectAttributes rttr) {
        log.info("book list 요청");

        if (result.hasErrors()) {
            return "/book/create";
        }

        // 서비스 호출
        Long code = bookService.insert(dto);

        // rttr.addAttribute(~~) : 주소줄에 따라간다. (? 뒤)
        // ~~?code=4 => 화면단에선 ${code, 2030}
        // rttr.addFlashAttribute(~~) : 세션이용함 => ${code}
        rttr.addFlashAttribute("code", code);
        return "redirect:/book/list";
    }

    // http://localhost:8080/book/list?page=1&size=10
    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book list 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        model.addAttribute("result", pageResultDTO);

    }

    // http://localhost:8080/book/read?code=4
    // http://localhost:8080/book/modify?code=4
    // 둘 다 접속 할거다.
    @GetMapping({ "/read", "/modify" })
    public void getRead(PageRequestDTO pageRequestDTO, Long code, Model model) {
        log.info("book get 요청 {}", code);

        BookDTO book = bookService.read(code);
        model.addAttribute("book", book); // book 밑 read 파악.
    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);

        // service 호출
        bookService.modify(dto);

        // read 로 이동
        rttr.addAttribute("code", dto.getCode());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book remove 요청 {}", code);

        // 서비스 호출
        bookService.remove(code);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/book/list";
    }

}
