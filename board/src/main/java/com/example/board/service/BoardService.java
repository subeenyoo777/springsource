package com.example.board.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {

        private final BoardRepository boardRepository;

        public BoardDTO getRow(Long bno) {
                Object[] entity = boardRepository.getBoardByBno(bno);

                return entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]);
        }

        public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

                Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                                Sort.by("bno").descending());
                Page<Object[]> result = boardRepository.list(pageable);

                // Function<T,R> : T => R로 변환
                Function<Object[], BoardDTO> fn = (entity -> entityToDto((Board) entity[0], (Member) entity[1],
                                (Long) entity[2]));

                List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());
                Long totalCount = result.getTotalElements();

                PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                                .dtoList(dtoList)
                                .totalCount(totalCount)
                                .pageRequestDTO(pageRequestDTO)
                                .build();

                return pageResultDTO;
        }

        private BoardDTO entityToDto(Board board, Member member, Long replyCount) {
                BoardDTO dto = BoardDTO.builder()
                                .bno(board.getBno())
                                .title(board.getTitle())
                                .content(board.getContent())
                                .email(member.getEmail())
                                .name(member.getName())
                                .replyCount(replyCount)
                                .createdDate(board.getCreatedDate())
                                .build();
                return dto;
        }
}
