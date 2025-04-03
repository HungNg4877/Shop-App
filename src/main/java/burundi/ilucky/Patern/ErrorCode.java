package burundi.ilucky.Patern;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "user-does-not-exist"),
    USER_EXIST(HttpStatus.BAD_REQUEST.value(), "user-already-exist"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "wrong-password"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST.value(), "invalid-refresh-token"),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "product-not-found"),
    NO_MORE_PRODUCT(HttpStatus.BAD_REQUEST.value(), "no-more-product"),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "category-not-found"),
    CATEGORY_EXIST(HttpStatus.BAD_REQUEST.value(), "category-exist"),
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "order-not-found"),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "payment-not-found")

    ;
    private final int code;
    private final String message;

    ErrorCode(int i, String s) {
        this.code = i;
        this.message = s;
    }
}
