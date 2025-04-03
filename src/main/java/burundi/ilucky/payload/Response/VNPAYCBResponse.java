package burundi.ilucky.payload.Response;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public class VNPAYCBResponse {
    private String txnRef;
    private BigDecimal amount;
    private String status;
    private String paymentTime;
}
