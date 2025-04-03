package burundi.ilucky.payload.Response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class VNPAYResponse {
    public String paymentUrl;
}
