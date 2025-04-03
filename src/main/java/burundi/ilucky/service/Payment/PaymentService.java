package burundi.ilucky.service.Payment;

import burundi.ilucky.Enum.OrderStatus;
import burundi.ilucky.Enum.PaymentStatus;
import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.Util.VNPayUtil;
import burundi.ilucky.config.VNPAYConfig;
import burundi.ilucky.model.Order;
import burundi.ilucky.model.Payment;
import burundi.ilucky.payload.Response.VNPAYResponse;
import burundi.ilucky.repository.OrderRepository;
import burundi.ilucky.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    public VNPAYResponse createVnPayPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));
        // Tạo txnRef (dùng orderId hoặc tạo một mã riêng)
        String txnRef = String.valueOf(orderId); // Hoặc UUID.randomUUID().toString();
        order.setTxnRef(txnRef);
        orderRepository.save(order);
        long amount = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_TxnRef", txnRef);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", "127.0.0.1");
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return VNPAYResponse.builder()
                .paymentUrl(vnPayConfig.getVnp_PayUrl() + "?" + queryUrl)
                .build();
    }

    public void updatePaymentStatus(String txnRef, String status) {
        Order order = orderRepository.findByTxnRef(txnRef) // Tìm theo txnRef
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));

        if ("00".equals(status)) {
            payment.setStatus(PaymentStatus.SUCCESS);
            order.setStatus(OrderStatus.PAID);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            order.setStatus(OrderStatus.CANCELED);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
}
