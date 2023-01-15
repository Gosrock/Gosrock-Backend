package band.gosrock.api.issuedTicket.service;


import band.gosrock.api.config.security.SecurityUtils;
import band.gosrock.api.issuedTicket.dto.response.RetrieveIssuedTicketListResponse;
import band.gosrock.api.issuedTicket.mapper.IssuedTicketMapper;
import band.gosrock.common.annotation.UseCase;
import band.gosrock.domain.domains.event.service.EventService;
import band.gosrock.domain.domains.issuedTicket.domain.IssuedTicket;
import band.gosrock.domain.domains.issuedTicket.service.IssuedTicketDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@UseCase
@RequiredArgsConstructor
public class ReadIssuedTicketsUseCase {

    private final IssuedTicketDomainService issuedTicketDomainService;
    private final IssuedTicketMapper issuedTicketMapper;
    private final EventService eventService;

    /**
     * 발급된 티켓 리스트 가져오기 API 일단 유즈케이스에 트랜잭션 걸어서 처리 IssuedTicket에 걸린 event와 user를 연관관계 매핑 없이 조회하려할 때
     * 로직이 너무 복잡해짐 => 일단 연관관계 매핑 걸어두고 나중에 QueryDsl 설정 들어오면 바꿔야 할 듯 => QueryDsl 추가 완료
     */
    public RetrieveIssuedTicketListResponse execute(
            Long page, Long eventId, String userName, String phoneNumber) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        // 조회 유저 권한 인증
        eventService.checkEventHost(currentUserId, eventId);
        Page<IssuedTicket> issuedTickets =
                issuedTicketDomainService.retrieveIssuedTickets(
                        page, eventId, userName, phoneNumber);
        return RetrieveIssuedTicketListResponse.of(issuedTickets);
    }
}
