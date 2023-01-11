package band.gosrock.domain.domains.order.service;


import band.gosrock.common.annotation.DomainService;
import band.gosrock.infrastructure.outer.api.tossPayments.client.PaymentsCancelClient;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.request.CancelPaymentsRequest;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.response.PaymentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CancelPaymentService {
    private final PaymentsCancelClient paymentsCancelClient;

    public PaymentsResponse cancelPayment(String orderUuid, String paymentKey, String reason) {
        log.info("환불처리 " + orderUuid + " : " + paymentKey);
        return paymentsCancelClient.execute(
                orderUuid,
                paymentKey,
                CancelPaymentsRequest.builder().cancelReason(reason).build());
    }
}
