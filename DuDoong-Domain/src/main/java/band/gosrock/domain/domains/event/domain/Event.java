package band.gosrock.domain.domains.event.domain;


import band.gosrock.domain.common.model.BaseTimeEntity;
import band.gosrock.domain.common.vo.DateTimePeriod;
import band.gosrock.domain.common.vo.EventInfoVo;
import band.gosrock.domain.common.vo.RefundInfoVo;
import band.gosrock.domain.domains.event.exception.EventCannotEndBeforeStartException;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_event")
public class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    // 호스트 정보
    private Long hostId;

    // 공연 이름
    private String name;

    // url 표시 이름 (unique)
    private String urlName;

    @Embedded private DateTimePeriod eventTime;

    @Embedded private EventPlace eventPlace;

    @Embedded private EventDetail eventDetail;

    // 이벤트 상태
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PREPARING;

    /*********** 미확정된 정보 ***********/
    // 공연 진행 시간
    private Long runTime;

    // 예매 시작 시각
    private LocalDateTime ticketingStartAt;

    // 예매 종료 시각
    private LocalDateTime ticketingEndAt;
    /*********** 미확정된 정보 ***********/

    public LocalDateTime getStartAt() {
        if (this.eventTime == null) {
            return null;
        }
        return this.eventTime.getStartAt();
    }

    public LocalDateTime getEndAt() {
        if (this.eventTime == null) {
            return null;
        }
        return this.eventTime.getEndAt();
    }

    /** 이벤트의 시작과 종료 시간을 지정 */
    public void setTime(LocalDateTime startAt, LocalDateTime endAt) {
        // 이벤트 종료가 시작보다 빠르면 안됨
        if (startAt.isAfter(endAt)) {
            throw EventCannotEndBeforeStartException.EXCEPTION;
        }
        this.eventTime = new DateTimePeriod(startAt, endAt);
    }

    /** 티켓팅 시작과 종료 시간을 지정 */
    public void setTicketingTime(LocalDateTime startAt, LocalDateTime endAt) {
        // 이벤트 종료가 시작보다 빠르면 안됨
        if (startAt.isAfter(endAt)) {
            throw EventCannotEndBeforeStartException.EXCEPTION;
        }
        this.ticketingStartAt = startAt;
        this.ticketingEndAt = endAt;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public void setEventDetail(EventDetail eventDetail) {
        this.eventDetail = eventDetail;
    }

    public void setEventPlace(EventPlace eventPlace) {
        this.eventPlace = eventPlace;
    }

    @Builder
    public Event(Long hostId, String name, Long runTime, String urlName) {
        this.hostId = hostId;
        this.runTime = runTime;
        this.name = name;
        this.urlName = urlName;
    }

    public RefundInfoVo getRefundInfoVo() {
        return RefundInfoVo.from(getStartAt());
    }

    public EventInfoVo toEventInfoVo() {
        return EventInfoVo.from(this);
    }
}
