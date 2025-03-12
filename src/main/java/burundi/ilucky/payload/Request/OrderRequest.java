package burundi.ilucky.payload.Request;

import burundi.ilucky.Enum.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items; // Danh sách sản phẩm đặt hàng
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // VNPAY, PAYPAL, COD
}
