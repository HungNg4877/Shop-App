package burundi.ilucky.controller;

import burundi.ilucky.payload.Request.OrderDetailRequest;
import burundi.ilucky.payload.Response.OrderDetailResponse;
import burundi.ilucky.service.OrderDetail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/create")
    public ResponseEntity<OrderDetailResponse> createOrderDetail(@RequestBody OrderDetailRequest request) {
        OrderDetailResponse response = orderDetailService.createOrderDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable Long id) {
//        OrderDetailResponse response = orderDetailService.getOrderDetailById(id);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDetailResponse> updateOrderDetail(
//            @PathVariable Long id, @RequestBody OrderDetailRequest request) {
//        OrderDetailResponse response = orderDetailService.updateOrderDetail(id, request);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
//        orderDetailService.deleteOrderDetail(id);
//        return ResponseEntity.noContent().build();
//    }
}
