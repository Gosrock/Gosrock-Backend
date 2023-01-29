package band.gosrock.domain.domains.ticket_item.exception;

import static band.gosrock.common.consts.DuDoongStatic.*;

import band.gosrock.common.annotation.ExplainError;
import band.gosrock.common.dto.ErrorReason;
import band.gosrock.common.exception.BaseErrorCode;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketItemErrorCode implements BaseErrorCode {
    @ExplainError("요청에서 보내준 티켓 상품 id 값이 올바르지 않을 때 발생하는 오류입니다.")
    TICKET_ITEM_NOT_FOUND(NOT_FOUND, "Ticket_Item_404_1", "티켓 아이템을 찾을 수 없습니다."),
    @ExplainError("요청에서 보내준 옵션 id 값이 올바르지 않을 때 발생하는 오류입니다.")
    OPTION_NOT_FOUND(NOT_FOUND, "Option_404_1", "옵션을 찾을 수 없습니다."),
    @ExplainError("주문 요청한 티켓 상품 재고가 부족할 때 발생하는 오류입니다.")
    TICKET_ITEM_QUANTITY_LACK(BAD_REQUEST, "Ticket_Item_400_1", "티켓 상품 재고가 부족합니다."),
    @ExplainError("주문 및 승인 요청 시 티켓 상품 재고보다 많은 양을 주문 시 발생하는 오류입니다.")
    TICKET_ITEM_QUANTITY_LESS_THAN_ZERO(
            BAD_REQUEST, "Ticket_Item_400_2", "티켓 아이템 재고가 0보다 작을 수 없습니다."),
    @ExplainError("제휴되지 않은 호스트가 티켓 가격을 0이 아닌 값으로 요청했을때 발생하는 오류입니다.")
    INVALID_TICKET_PRICE(BAD_REQUEST, "Ticket_Item_400_3", "티켓 가격을 설정할 수 없습니다."),
    @ExplainError("예매 취소 및 티켓 취소 요청 시 티켓 상품 공급량보다 많은 양이 반환될 때 발생하는 오류입니다.")
    TICKET_ITEM_QUANTITY_LARGER_THAN_SUPPLY_COUNT(
            BAD_REQUEST, "Ticket_Item_400_4", "공급량보다 많은 티켓 아이템 재고가 설정되었습니다."),
    @ExplainError("요청에서 보내준 옵션그룹 id 값이 올바르지 않을 때 발생하는 오류입니다.")
    OPTION_GROUP_NOT_FOUND(NOT_FOUND, "Option_Group_404_1", "옵션그룹을 찾을 수 없습니다."),
    @ExplainError("적용할 옵션이 해당 이벤트 소속이 아닐 때 발생하는 오류입니다.")
    INVALID_OPTION_GROUP(BAD_REQUEST, "Option_Group_400_1", "해당 이벤트 소속 옵션그룹이 아닙니다."),
    @ExplainError("옵션을 적용할 상품이 해당 이벤트 소속이 아닐 때 발생하는 오류입니다.")
    INVALID_TICKET_ITEM(BAD_REQUEST, "Ticket_Item_400_5", "해당 이벤트 소속 티켓상품이 아닙니다."),
    @ExplainError("해당 티켓상품에 이미 적용된 옵션일 경우 발생하는 오류입니다.")
    DUPLICATED_ITEM_OPTION_GROUP(BAD_REQUEST, "Item_Option_Group_400_1", "이미 적용된 옵션입니다."),
    OPTION_ANSWER_NOT_CORRECT(
            BAD_REQUEST, "Option_400_1", "옵션에 대한 답변이 올바르지 않습니다. T/F형일 경우 예 아니요 로 보내주세요."),
    FORBIDDEN_OPTION_CHANGE(BAD_REQUEST, "Item_Option_Group_400_2", "옵션 변경이 불가능한 상태입니다.");
    private Integer status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
