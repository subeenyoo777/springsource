package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@Service
@RequiredArgsConstructor
public class ToDoService {
    private final TodoRepository todoRepository;

    private final ModelMapper modelMapper;

    public List<ToDoDTO> list(boolean completed) {
        List<ToDo> list = todoRepository.findByCompleted(completed);

        // // ToDo entity => ToDoTo 변경 후 리턴
        // List<ToDoDTO> todos = new ArrayList<>();
        // list.forEach(todo -> {
        // ToDoDTO dto = modelMapper.map(todo, ToDoDTO.class);
        // todos.add(dto);
        // });

        List<ToDoDTO> todos = list.stream()
                .map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());

        return todos;

    }

}
