package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todo")
@Controller
public class ToDoController {
    // @Autowired//선언시 마다 매번 해줘야 한다.
    private final ToDoService toDoService;

    @GetMapping("/create")
    public void getCreate() {
        log.info("todo 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(ToDoDTO dto, RedirectAttributes rttr) {
        log.info("todo 입력 {}", dto);

        Long id = toDoService.create(dto);

        // read로 이동
        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

    @GetMapping("/remove")
    public String getRemove(Long id) {
        log.info("삭제 {}", id);

        toDoService.remove(id);
        return "redirect:/todo/list";
    }

    @GetMapping("/list")
    public void getList(@RequestParam(defaultValue = "0") boolean completed, Model model) {
        log.info("전체 todo 가져오기 {}", completed);
        List<ToDoDTO> todos = toDoService.list(completed);
        model.addAttribute("todos", todos);
        // 어떤 목록을 보여주는가?(완료/미완료)
        model.addAttribute("completed", completed);
    }

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);

        ToDoDTO dto = toDoService.read(id);
        model.addAttribute("dto", dto);

    }

    @PostMapping("/modify")
    public String postCompleted(ToDoDTO dto, RedirectAttributes rttr) {
        log.info("수정 {}", dto);

        // 서비스 호출
        Long id = toDoService.changeCompleted(dto);

        // read 작업
        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

}
