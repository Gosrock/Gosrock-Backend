package band.gosrock.domain.domains.issuedTicket.repository;

import static band.gosrock.domain.domains.issuedTicket.domain.QIssuedTicket.issuedTicket;
import static band.gosrock.domain.domains.issuedTicket.domain.QIssuedTicketOptionAnswer.issuedTicketOptionAnswer;

import band.gosrock.domain.domains.issuedTicket.domain.IssuedTicket;
import band.gosrock.domain.domains.issuedTicket.dto.condition.IssuedTicketCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class IssuedTicketCustomRepositoryImpl implements IssuedTicketCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<IssuedTicket> searchToPage(IssuedTicketCondition condition, Pageable pageable) {
        List<IssuedTicket> issuedTickets =
                queryFactory
                        .selectFrom(issuedTicket)
                        .where(
                                eventIdEq(condition.getEventId()),
                                userNameContains(condition.getUserName()),
                                phoneNumberContains(condition.getPhoneNumber()))
                        .orderBy(issuedTicket.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Long> countQuery =
                queryFactory
                        .select(issuedTicket.count())
                        .from(issuedTicket)
                        .where(
                                eventIdEq(condition.getEventId()),
                                userNameContains(condition.getUserName()),
                                phoneNumberContains(condition.getPhoneNumber()));

        return PageableExecutionUtils.getPage(issuedTickets, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<IssuedTicket> find(Long issuedTicketId) {
        IssuedTicket findIssuedTicket =
                queryFactory
                        .selectFrom(issuedTicket)
                        .leftJoin(issuedTicket.issuedTicketOptionAnswers, issuedTicketOptionAnswer)
                        .fetchJoin()
                        .where(issuedTicket.id.eq(issuedTicketId))
                        .fetchOne();
        return Optional.ofNullable(findIssuedTicket);
    }

    private BooleanExpression eventIdEq(Long eventId) {
        return eventId == null ? null : issuedTicket.eventId.eq(eventId);
    }

    private BooleanExpression userNameContains(String userName) {
        return userName == null ? null : issuedTicket.userInfo.userName.contains(userName);
    }

    private BooleanExpression phoneNumberContains(String phoneNumber) {
        return phoneNumber == null ? null : issuedTicket.userInfo.phoneNumber.contains(phoneNumber);
    }
}
