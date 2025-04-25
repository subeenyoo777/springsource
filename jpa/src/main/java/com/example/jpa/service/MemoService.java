package com.example.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// repository 메소드 호출 후 결과 받음.
public class MemoService {

    private final MemoRepository memoRepository;
    private final ModelMapper modelMapper;

    public List<MemoDTO> getList() {
        List<Memo> list = memoRepository.findAll();

        // Memo => MemoDTO 옮기기
        List<MemoDTO> memos = list.stream()
                // .map(memo -> entityToDto(memo))
                .map(memo -> modelMapper.map(memo, MemoDTO.class))
                .collect(Collectors.toList());
        return memos;
    }

    // list.stream().forEach(memo -> System.out.println(memo));
    // List<MemoDTO> memos = list.stream()
    // .map(memo -> entityToDto(memo))
    // .collect(Collectors.toList());

    // return memos;
    // }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity => dto
        // MemoDTO dto = entityToDto(memo);
        // MemoDTO dto = entityToDto(memo);//직접 메서드 만든 경우

        // modelMapper.map(원본, 변경할 타입)
        MemoDTO dto = modelMapper.map(memo, MemoDTO.class);
        return dto;

    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update 실행 => 수정된 Memo return 해줌.
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private Memo dtoToEntity(MemoDTO memoDTO) {
        Memo memo = Memo.builder()
                .mno(memoDTO.getMno())
                .memoText(memoDTO.getMemoText())
                .build();
        return memo;
    }

    private MemoDTO entityToDto(Memo memo) {
        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updatedDate(memo.getUpdatedDate())
                .build();
        return dto;
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo 는 MemoDTO 에 저장
        // MemoDTO => Memo 변환

        // Memo memo = dtoToEntity(dto);
        Memo memo = modelMapper.map(dto, Memo.class);

        // 새로 저장한 memo 리턴됨
        memo = memoRepository.save(memo);
        return memo.getMno();

    }
}
