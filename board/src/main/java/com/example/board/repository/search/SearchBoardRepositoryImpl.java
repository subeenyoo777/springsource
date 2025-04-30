package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2 // lombok
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        log.info("SearchBoard");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));

        // 댓글 갯수
        // r.board_id = b.bno
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);// tuple: 행 하나 담는 구조

        log.info("============");
        log.info(query);
        log.info("============");

        // sort 생성
        // PageRequest.of(0,10,Sort.by("bno").descending()) --> bno로 desending
        Sort sort = pageable.getSort();
        // sort 기준이 여러개일 수 있으므로
        sort.stream().forEach(order -> {
            // important com.querydsl.core.types.Order
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Board> ordeBuilder = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, ordeBuilder.get(prop)));
        });

        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        // 전체 리스트 + Sort 적용됨

        // <page 처리>
        tuple.offset(pageable.getOffset());// Offset: 처음위치에서 얼마나 떨어져있는지
        // 10
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        // 전체갯수
        long count = tuple.fetchCount();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Object[] getBoardByBno(Long bno) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));
        query.where(board.bno.eq(bno));

        // 댓글 갯수
        // r.board_id = b.bno
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);// tuple: 행 하나 담는 구조

        Tuple row = tuple.fetchFirst();
        return row.toArray();

    }

}
