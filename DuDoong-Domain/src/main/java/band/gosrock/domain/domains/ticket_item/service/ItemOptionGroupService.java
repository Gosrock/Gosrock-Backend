package band.gosrock.domain.domains.ticket_item.service;


import band.gosrock.common.annotation.DomainService;
import band.gosrock.domain.common.aop.redissonLock.RedissonLock;
import band.gosrock.domain.domains.ticket_item.adaptor.OptionAdaptor;
import band.gosrock.domain.domains.ticket_item.adaptor.TicketItemAdaptor;
import band.gosrock.domain.domains.ticket_item.domain.OptionGroup;
import band.gosrock.domain.domains.ticket_item.domain.TicketItem;
import band.gosrock.domain.domains.ticket_item.repository.TicketItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemOptionGroupService {

    private final TicketItemAdaptor ticketItemAdaptor;
    private final OptionAdaptor optionAdaptor;
    private final TicketItemRepository ticketItemRepository;

    @RedissonLock(LockName = "티켓관리", identifier = "ticketItemId")
    @Transactional
    public TicketItem addItemOptionGroup(Long ticketItemId, Long optionGroupId, Long eventId) {

        TicketItem ticketItem = ticketItemAdaptor.queryTicketItem(ticketItemId);
        OptionGroup optionGroup = optionAdaptor.queryOptionGroup(optionGroupId);

        // 해당 eventId에 속해 있는 티켓 아이템, 옵션그룹이 맞는지 확인
        ticketItem.checkEventId(eventId);
        optionGroup.checkEventId(eventId);

        ticketItem.addItemOptionGroup(optionGroup);
        return ticketItemRepository.save(ticketItem);
    }
}
