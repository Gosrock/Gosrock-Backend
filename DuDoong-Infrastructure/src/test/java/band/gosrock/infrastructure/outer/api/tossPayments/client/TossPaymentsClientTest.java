package band.gosrock.infrastructure.outer.api.tossPayments.client;

import static org.junit.jupiter.api.Assertions.*;

import band.gosrock.common.DuDoongCommonApplication;
import band.gosrock.common.properties.TossPaymentsProperties;
import band.gosrock.infrastructure.config.feign.FeginCommonConfig;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.request.CancelPaymentsRequest;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.request.ConfirmPaymentsRequest;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.request.CreatePaymentsRequest;
import band.gosrock.infrastructure.outer.api.tossPayments.dto.response.PaymentsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// @AutoConfigureTestFeign
@ActiveProfiles({"common"})
@SpringBootTest(classes = {FeginCommonConfig.class, DuDoongCommonApplication.class})
class TossPaymentsClientTest {
    @Autowired TossPaymentsClient tossPaymentsClient;
    @Autowired TossPaymentsProperties tossPaymentsProperties;

    @Test
    public void 결제_요청_테스트() {
        String secretKey = tossPaymentsProperties.getSecretKey();
        CreatePaymentsRequest createPaymentsRequest =
                CreatePaymentsRequest.builder()
                        .successUrl("http://localhost:8080/return-url")
                        .failUrl("http://localhost:8080/failurl-url")
                        .amount(10000L)
                        .orderId("abcd1233")
                        .method("카드")
                        .orderName("주문1")
                        .build();

        PaymentsResponse createPaymentResponse =
                tossPaymentsClient.createPayments(createPaymentsRequest);
    }

    @Test
    public void 결제_승인_테스트() {
        ConfirmPaymentsRequest confirmPaymentsRequest =
                ConfirmPaymentsRequest.builder()
                        .paymentKey("qKl56WYb7w4vZnjEJeQVxYQd9zBz9rPmOoBN0k12dzgRG9px")
                        .amount(10000L)
                        .orderId("abcd1233")
                        .build();
        PaymentsResponse confirmPaymentResponse =
                tossPaymentsClient.confirmPayments(confirmPaymentsRequest);

        String orderId = confirmPaymentResponse.getOrderId();
        String paymentKey = confirmPaymentResponse.getPaymentKey();
    }

    @Test
    public void 결제_취소_테스트() {
        CancelPaymentsRequest cancelPaymentsRequest =
                CancelPaymentsRequest.builder().cancelReason("취소 사유").build();

        PaymentsResponse paymentsResponse =
                tossPaymentsClient.cancelPayments(
                        "qKl56WYb7w4vZnjEJeQVxYQd9zBz9rPmOoBN0k12dzgRG9px", cancelPaymentsRequest);
    }
}
