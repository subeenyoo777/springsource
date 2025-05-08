package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// @Builder: PageRequestDTO.builder().build()
//-----------> (객체 생성할 때 builder field 사용 가능)

// @SuperBuilder: 상속관계에서도 안전하게 사용 / 서브 클래스가 이 클래스를 상속할 때 함께 빌더로 생성 가능.
// -------------> (객체 생성할 때 builder field 사용 가능)

// @Builder.Default : 빌더로 객체를 생성할 때 필드가 포함 안되는 경우 사용할 기본값 지정
//                  PageRequestDTO.builder().build() => page = 1, size = 10

@AllArgsConstructor
@NoArgsConstructor
@Data // 데이터를 한꺼번에 갖고 있음.

@SuperBuilder
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    // 키워드 검색 시
    private String type;
    private String keyword;

}
