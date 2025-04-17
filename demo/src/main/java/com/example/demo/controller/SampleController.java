package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.SampleDTO;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2

public class SampleController {

    @GetMapping("/sample")
    public void getSample(Model model) {
        log.info("sample 페이지 요청");

        model.addAttribute("name", "hong");

        SampleDTO sampleDTO = SampleDTO.builder()
                .id(1L)
                .first("hong")
                .last("dong")
                .regDateTime(LocalDateTime.now())
                .build();

        model.addAttribute("dto", sampleDTO);

        List<SampleDTO> list = new ArrayList<>();

        for (long i = 0; i < 20; i++) {
            sampleDTO = SampleDTO.builder()
                    .id(i)
                    .first("hong" + i)
                    .last("dong" + i)
                    .regDateTime(LocalDateTime.now())
                    .build();
            list.add(sampleDTO);
        }
        model.addAttribute("list", list);
        model.addAttribute("title", "This is a just sample");
        model.addAttribute("now", new Date());

        model.addAttribute("price", 123456789);
        model.addAttribute("options", Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD"));

    }

    @GetMapping("/ex1")
    public void getEx1(String param1, int param2) {
        log.info("파라미터 확인");
        log.info("{}, {}", param1, param2);
    }

    @GetMapping("/content")
    public void getContent() {

    }

}
