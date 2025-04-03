package burundi.ilucky.payload.Response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal totalMoney;
}

