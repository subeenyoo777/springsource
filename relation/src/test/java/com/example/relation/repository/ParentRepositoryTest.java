package com.example.relation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.relation.entity.cascasde.Child;
import com.example.relation.entity.cascasde.Parent;
import com.example.relation.repository.cascade.ChildRepository;
import com.example.relation.repository.cascade.ParentRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Test
    public void testInsert() {
        Parent parent = new Parent();
        parent.setName("parent1");
        parentRepository.save(parent);

        Child child = new Child();
        child.setName("child1");
        child.setParent(parent);
        childRepository.save(child);

        child = new Child();
        child.setName("child2");
        child.setParent(parent);
        childRepository.save(child);

    }

    @Test
    public void testInsert2() {
        // 새로 부모를 저장하면서 자식도 같이 저장/길동이춘향이보검이 자식들
        Parent parent = new Parent();
        parent.setName("parent3");

        parent.getChilds().add(Child.builder().name("홍길동").parent(parent).build());
        parent.getChilds().add(Child.builder().name("성춘향").parent(parent).build());
        parent.getChilds().add(Child.builder().name("박보검").parent(parent).build());

        // childRepository.save(child); 호출하지 않고도 child 저장이 됨!
        parentRepository.save(parent);

    }

    @Test
    public void testDelete() {
        // 부모 삭제 시 자식도 함께 삭제( CascadeType.REMOVE )
        parentRepository.deleteById(1L);

    }

    @Commit
    @Transactional
    @Test
    public void testDelete2() {
        // 부모입장에서
        Parent parent = parentRepository.findById(7L).get();
        // 자식 조회
        // System.out.println(parent.getChilds());

        parent.getChilds().remove(0);// 홍길동 고아 객체 상태됨( orphanRemoval = true)
        System.out.println(parent.getChilds());
        parentRepository.save(parent);
    }

}
