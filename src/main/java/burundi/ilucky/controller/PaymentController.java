package burundi.ilucky.controller;

import burundi.ilucky.payload.Response.APIResponse;
import burundi.ilucky.payload.Response.VNPAYCBResponse;
import burundi.ilucky.payload.Response.VNPAYResponse;
import burundi.ilucky.service.Payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay/{orderId}")
    public APIResponse<VNPAYResponse> pay (@PathVariable Long orderId) {

        return new APIResponse<>(HttpStatus.OK.value(), "Success", paymentService.createVnPayPayment(orderId));
    }
    @GetMapping("/vn-pay-callback")
    public APIResponse<VNPAYCBResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String txnRef = request.getParameter("vnp_TxnRef");
        String amountStr = request.getParameter("vnp_Amount");
        String paymentTime = request.getParameter("vnp_PayDate");

        // Cập nhật trạng thái thanh toán
        paymentService.updatePaymentStatus(txnRef, status);

        // Chuyển amount về đơn vị VND
        BigDecimal amount = new BigDecimal(amountStr).divide(BigDecimal.valueOf(100));

        VNPAYCBResponse vnCBPayResponse = VNPAYCBResponse.builder()
                .txnRef(txnRef)
                .amount(amount)
                .status("00".equals(status) ? "SUCCESS" : "FAILED")
                .paymentTime(paymentTime)
                .build();

        if ("00".equals(status)) {
            return new APIResponse<>(HttpStatus.OK.value(), "Payment Success", vnCBPayResponse);
        } else {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Payment Failed", vnCBPayResponse);
        }
    }

}
