package com.example.jpa.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.dto.MemoDTO;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class MemoServiceTest {
    @Autowired
    private MemoService memoService;

    @Test
    public void getList() {
        List<MemoDTO> list = memoService.getList();
        list.forEach(dto -> System.out.println(dto));
    }

    @Test
    public void getRowTest() {
        MemoDTO dto = memoService.getRow(2L);
        System.out.println(dto);
    }

    @Test
    public void memoDeleteTest() {
        memoService.memoDelete(5L);
    }

    @Test
    public void memoCreate() {
        MemoDTO dto = MemoDTO.builder().memoText("memo추가").build();
        System.out.println("새롭게 추가된 mno" + memoService.memoCreate(dto));
    }

}
