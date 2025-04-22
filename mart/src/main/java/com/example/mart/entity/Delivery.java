package com.example.mart.entity;

import com.example.mart.entity.constant.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // 테이블과 관련됨, 필수사항

public class Delivery extends BaseEntity {
    // 하나의 주문은 하나의 배송과 연결된다

    // 어디로 접근하는게 나은 방법인지 생각해보기
    // 주문 -> 배송 (이걸로 해보자.)
    // 배송 -> 주문

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    // 배송상태 : 준비, 배송..> enum으로만듦
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    // 배송과 관련있는 주문 조회
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

}
