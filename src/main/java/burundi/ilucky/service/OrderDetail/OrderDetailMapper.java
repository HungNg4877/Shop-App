package burundi.ilucky.service.OrderDetail;

import burundi.ilucky.model.OrderDetail;
import burundi.ilucky.payload.Response.OrderDetailResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailMapper {
    public OrderDetailResponse mapToResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .quantity(orderDetail.getQuantity())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }
}
