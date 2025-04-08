package burundi.ilucky.controller;

import burundi.ilucky.payload.Response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//    @PostMapping
//    public ResponseEntity<CreateCartResponse> createCart(CreateCartRequest request) {
//        CreateCartRespnse response = cartService.createCart(request);
//        return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(),"", response));
//    }
//}
