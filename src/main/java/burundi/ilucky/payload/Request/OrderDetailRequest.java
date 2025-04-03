package burundi.ilucky.payload.Request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailRequest {
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal totalMoney;
}

