package burundi.ilucky.controller;

import burundi.ilucky.model.dto.OrderDTO.OrderDTO;
import burundi.ilucky.payload.Request.OrderRequest;
import burundi.ilucky.payload.Response.OrderResponse;
import burundi.ilucky.service.OrderService.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderDTO orderDTO = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
    // API cập nhật đơn hàng
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDTO> updateOrder(
//            @PathVariable Long id,
//            @RequestBody OrderRequest orderRequest) {
//        OrderDTO updatedOrder = orderService.(id, orderRequest);
//        return ResponseEntity.ok(updatedOrder);
//    }

    // API xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully!");
    }
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
//        return ResponseEntity.ok(orderService.getOrderById(orderId));
//    }
}
