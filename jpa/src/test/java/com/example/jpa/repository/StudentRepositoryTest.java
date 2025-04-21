package com.example.jpa.repository;

import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // = test용 클래스
public class StudentRepositoryTest {
    // @Autowired : studentRepository를 spring boot framwork에게 생성해 대입해주길 요청
    @Autowired // = new StudentRepository()
    private StudentRepository studentRepository;

    // CRUD test
    // Repository, Entity 확인
    // C(insert): save(Entity) > insert
    // U(update): save(Entity) > update
    // C와 U 구별을 어떻게 하는가?
    // 원본과 변경된 부분이 있다면 update 로 실행해줌

    @Test // 테스트 메서드(해당 메서드 리턴 타입은 무조건 void)
    public void insertTest() {
        // Entity 생성
        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();
            studentRepository.save(student);
        });
    }

    // Student student = Student.builder()
    // .name("홍길동")
    // .grade(Grade.JUNIOR)
    // .gender("M")
    // .build();

    // studentRepository.save(student);
    // }

    @Test
    public void updateTest() {

        // findById : select * from 테이블명 where id = 1

        Student student = studentRepository.findById(1L).get();
        student.setGrade(Grade.SENIOR);

        // update : 바뀌는 칼람뿐 아니라 "전체 set"하게 되있다.
        studentRepository.save(student);

        // update
        // studenttbl
        // set
        // c_date_time=?,
        // c_date_time2=?,
        // gender=?,
        // grade=?,
        // name=?,
        // u_date_time=?,
        // u_date_time2=?
        // where
        // id=?

    }

    @Test
    public void selectOneTest() {

        // Optional<Student>
        // org.springframework.data.repository.CrudRepository.findById(Long id)
        // Retrieves an entity by its id.
        // Optional<Student> student = studentRepository.findById(1L);

        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }

        // Student student = studentRepository.findById(3L).get();
        Student student = studentRepository.findById(3L).orElseThrow(EntityNotFoundException::new);
        System.out.println(student);
    }

    @Test
    public void selectTest() {
        // List<Student> list = studentRepository.findAll();

        // for (Student student : list) {
        // System.out.println(student);
        // }

        studentRepository.findAll().forEach(student -> System.out.println(student));

    }

    @Test
    public void deleteTest() {

        // Student student = studentRepository.findById(11L).get();
        // studentRepository.delete(student); // entity 자체를 넣어달라.

        studentRepository.deleteById(10L); // id 이용해 제거
    }
}
