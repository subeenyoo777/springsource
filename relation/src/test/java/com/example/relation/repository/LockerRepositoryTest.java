package com.example.relation.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.sports.SportsMember;
import com.example.relation.repository.sports.LockerRepository;
import com.example.relation.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsRepository;

    // 단방향
    @Test
    public void testInsert() {
        // Locker 생성
        IntStream.range(1, 6).forEach(i -> {
            Locker locker = Locker.builder().name("locker" + i).build();
            lockerRepository.save(locker);

        });

        // Locker 회원 설정
        LongStream.range(1, 6).forEach(i -> {
            SportsMember sportsMember = SportsMember.builder()
                    .locker(Locker.builder().id(i).build())
                    .name("member" + i).build();

            sportsRepository.save(sportsMember);

        });

    }

    // 개별 조회
    @Test
    public void testRead1() {
        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsRepository.findById(1L).get());
    }

    @Transactional
    @Test
    public void testRead2() {

        SportsMember sportsMember = sportsRepository.findById(1L).get();
        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker());
    }

    @Test
    public void testRead3() {
        // 3번 회원의 이름을 홍길동으로 변환
        SportsMember sportsMember = sportsRepository.findById(3L).get();
        sportsMember.setName("홍길동");
        sportsRepository.save(sportsMember);
    }

    @Test
    public void testDelete() {
        // 5번 회원 삭제
        sportsRepository.deleteById(5L);
    }

    @Test
    public void testDelete2() {
        // 4번 locker 삭제 시 // (무결성 제약 - 4번 locker 를 할당받은 member 존재)
        // 4번 회원에게 다른 라커를 지정
        // 회원 삭제 후 4번을 삭제

        // 4번 회원에게 5번 locker 할당
        SportsMember member = sportsRepository.findById(4L).get();
        Locker locker = lockerRepository.findById(5L).get();

        member.setLocker(locker);
        sportsRepository.save(member);

        lockerRepository.deleteById(4L);
    }

    // --------------------------------
    // locker => sportsMember 접근
    // --------------------------------
    @Test
    public void testRead4() {
        Locker locker = lockerRepository.findById(1L).get();

        System.out.println(locker);
        System.out.println(locker.getSportsMember());
    }

}
