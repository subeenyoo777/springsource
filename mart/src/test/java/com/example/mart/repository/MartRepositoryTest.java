package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Autowired // 해줘야 nullException 해결
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void testMemberInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .street("724-11" + i)
                    .zipcode("1650" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void testItemInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("티셔츠" + i)
                    .price(i * 20000)
                    .stockQuantity(i * 5)
                    .build();

            itemRepository.save(item);
        });
    }

    // 주문하다 : (주문 테이블 + orderItem 테이블)insert
    @Test
    public void testOrderInsert() {

        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        orderRepository.save(order);

        // 주문과 관련된 상품은 OrderItem에 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(2L).get())
                .order(order)
                .orderPrice(39600)
                .count(1)
                .build();

        orderItemRepository.save(orderItem);

        orderItem = OrderItem.builder()
                .item(itemRepository.findById(3L).get())
                .order(order)
                .orderPrice(45000)
                .count(1)
                .build();

        orderItemRepository.save(orderItem);
    }

    @Test
    public void testOrderInsert2() {
        // order 저장 시 OrderItem 같이 저장
        // cascade CascadeType.PERSIST 설정
        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        // 주문과 관련된 상품은 OrderItem에 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(2L).get())
                .order(order)
                .orderPrice(39600)
                .count(1)
                .build();

        // orderItemRepository.save(orderItem);
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);
    }

    // <조회>
    @Transactional
    @Test
    public void testRead1() {
        // 주문 조회(주문번호 이용)
        Order order = orderRepository.findById(1L).get();
        System.out.println(order);

        // 주문자 정보 조회
        System.out.println(order.getMember());
    }

    @Transactional // = 지금 필요함.
    @Test
    public void testRead2() {
        // 특정 회원의 주문 내역 전체 조회
        Member member = memberRepository.findById(1L).get();
        System.out.println(member.getOrders());
    }

    @Transactional
    @Test
    public void testRead3() {
        // 주문상품의 정보 조회
        OrderItem orderItem = orderItemRepository.findById(1L).get();
        System.out.println(orderItem);

        // 주문상품의 상품명 조회
        System.out.println(orderItem.getItem().getName()); // 결과값 : 티셔츠1

        // 주문 상품을 주문한 고객 정보 조회
        Member member = orderItem.getOrder().getMember();
        System.out.println(member);
        // 결과값 : Member(id=1, name=user1, zipcode=16501, city=null, street=724-111)
    }

    @Transactional
    @Test
    public void testRead4() {
        Order order = orderRepository.findById(3L).get();
        System.out.println(order);

        // 양방향을 하면 반대 방향에서 여는 것도 허용됨; 양방향을 여는 이유는 조회가 편하기 때문이다.
        // 주문을 통해서 주문 아이템 조회
        order.getOrderItems().forEach(item -> System.out.println(item));
        // 출력 :
        // OrderItem(id=3, orderPrice=39600, count=1)
        // OrderItem(id=4, orderPrice=45000, count=1)
    }

    // 삭제
    @Test
    public void testDelete1() {
        // memberRepository.deleteById(5L); 아무런 연관이 없는 건 잘 삭제가 되나,

        // member id 로 주문 찾아오기 => 메서드 생성
        // orderRepository.find

        // 주문상품 취소 :
        // 주문 취소 :
        // 멤버 제거 :

        memberRepository.deleteById(1L);

    }

    @Test
    public void testDelete2() {
        // 주문 아이템 제거
        orderItemRepository.deleteById(2L);

        // 주문 제거
        orderRepository.deleteById(2L);

    }

    @Test
    public void testDelete3() {
        // 부모 쪽에 CasCade 작성
        // 주문 제거 시 => 주문 상품 같이 제거
        orderRepository.deleteById(1L);
    }

    @Commit
    @Transactional
    @Test
    public void testDelete4() {
        Order order = orderRepository.findById(5L).get();

        // 현재 주문과 연결된 주문 상품 조회
        System.out.println(order.getOrderItems());

        // 첫번째 자식 제거
        order.getOrderItems().remove(0);

        // orphanRemoval = true
        orderRepository.save(order);

    }

    @Test
    public void testDeliveryInsert() {
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);

        // 주문과 연결
        Order order = orderRepository.findById(4L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testDeliveryInsert2() {
        // persist
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        // deliveryRepository.save(delivery);

        // 주문과 연결
        // merge 형식으로 처리
        Order order = orderRepository.findById(5L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Transactional
    @Test
    public void testDeliveryRead() {
        // 배송조회
        System.out.println(deliveryRepository.findById(1L));

        // 주문과 관련있는 배송 조회
        Order order = orderRepository.findById(4L).get();
        System.out.println(order.getDelivery().getDeliveryStatus());

    }

    // 배송과 관련있는 주문 조회X => 양방향
    @Transactional
    @Test
    public void testDeliveryRead2() {
        // 배송조회
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println("주문 조회" + delivery.getOrder());
        System.out.println("주문자 조회" + delivery.getOrder().getMember());
        System.out.println("주문 아이템 조회" + delivery.getOrder().getOrderItems());

        // 주문자 조회Member(id=1, name=user1, zipcode=16501, city=null, street=724-111).
        // 주문 아이템 조회[].
    }

    @Test
    public void deleteTest() {
        // order 지우면서 (배송정보,주문상품)한꺼번에 제거
        orderRepository.deleteById(5L);
    }

    // =======================================================================================================================
    // ------------------------------------------------------CATEGOYRY------------------------------------------------------
    // =======================================================================================================================
    @Test
    public void testCategoryItemInsert01() {
        // Category category1 = Category.builder().name("가전제품").build();
        // Category category2 = Category.builder().name("식품").build();
        // Category category3 = Category.builder().name("생활용품").build();

        // categoryRepository.save(category1);
        // categoryRepository.save(category2);
        // categoryRepository.save(category3);

        Category category1 = categoryRepository.findById(1L).get();
        Category category2 = categoryRepository.findById(2L).get();
        Category category3 = categoryRepository.findById(3L).get();

        // // 아이템 입력
        Item item1 = Item.builder().name("TV").price(2500000).stockQuantity(15).build();
        itemRepository.save(item1);

        CategoryItem categoryItem = CategoryItem.builder().category(category1).item(item1).build();
        categoryItemRepository.save(categoryItem);

        item1 = Item.builder().name("콩나물").price(1200).stockQuantity(5).build();
        itemRepository.save(item1);

        categoryItem = CategoryItem.builder().category(category2).item(item1).build();
        categoryItemRepository.save(categoryItem);

        item1 = Item.builder().name("샴푸").price(12000).stockQuantity(7).build();
        itemRepository.save(item1);

        categoryItem = CategoryItem.builder().category(category3).item(item1).build();
        categoryItemRepository.save(categoryItem);

    }

    @Transactional
    @Test
    public void readCateItem() {
        // CategoryItem => Category, CategoryItem => Item
        // 카테고리 아이템으로 조회
        CategoryItem categoryItem = categoryItemRepository.findById(1L).get();
        System.out.println(categoryItem);
        System.out.println(categoryItem.getCategory().getName());
        System.out.println(categoryItem.getItem().getName());

        Category category = categoryRepository.findById(1L).get();
        category.getCategoryItems().forEach(item -> {
            System.out.println(item.getItem());
        });
    }

    // queryDesl test
    @Test
    public void membersTest() {
        List<Member> list = orderRepository.members();
        System.out.println(list);
    }

    @Test
    public void itemTest() {
        List<Item> list = orderRepository.items();
        System.out.println(list);
    }

    @Test
    public void joinTest() {
        List<Object[]> list = orderRepository.joinTest();
        for (Object[] objects : list) {
            // System.out.println(Arrays.toString(objects));
            Order order = (Order) objects[0];
            Member member = (Member) objects[1];
            OrderItem orderItem = (OrderItem) objects[2];

            System.out.println(order);
            System.out.println(member);
            System.out.println(orderItem);
        }
    }

    @Test
    public void subQueryTest() {
        List<Object[]> list = orderRepository.subQueryTest();
        for (Object[] objects : list) {
            Order order = (Order) objects[0];
            Member member = (Member) objects[1];
            OrderItem orderItem = (OrderItem) objects[2];
            Long orderCnt = (Long) objects[3];
            Long orderSum = (Long) objects[4];

            System.out.println(order);
            System.out.println(member);
            System.out.println(orderItem);
            System.out.println(orderCnt);
            System.out.println(orderSum);
        }
    }

}
