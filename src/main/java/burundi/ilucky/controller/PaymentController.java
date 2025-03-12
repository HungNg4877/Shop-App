package burundi.ilucky.controller;

import burundi.ilucky.payload.Response.APIResponse;
import burundi.ilucky.payload.Response.VNPAYResponse;
import burundi.ilucky.service.Payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public APIResponse<VNPAYResponse> pay(HttpServletRequest request) {
        return new APIResponse<>(HttpStatus.OK.value(), "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public APIResponse<VNPAYResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        VNPAYResponse vnPayResponse = VNPAYResponse.builder()
                .paymentUrl("")
                .build();
        if (status.equals("00")) {
            return new APIResponse<>(HttpStatus.OK.value(), "Success", vnPayResponse);
        } else {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }
    }
}
