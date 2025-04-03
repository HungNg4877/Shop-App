package burundi.ilucky.payload.Request;

import burundi.ilucky.Enum.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {

    private Long userId;

    private List<OrderItemRequest> items; // Danh sách sản phẩm đặt hàng

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String note; // Ghi chú đơn hàng

    private String shippingMethod; // Giao hàng nhanh, tiêu chuẩn, nội địa...

    private String shippingAddress;

    private LocalDate shippingDate; // Ngày dự kiến giao hàng

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // VNPAY, PAYPAL, COD
}
