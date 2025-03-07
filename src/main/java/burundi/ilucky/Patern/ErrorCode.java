package burundi.ilucky.Patern;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "user-does-not-exist"),
    USER_EXIST(HttpStatus.BAD_REQUEST.value(), "user-already-exist"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "wrong-password"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST.value(), "invalid-refresh-token")
    ;
    private final int code;
    private final String message;

    ErrorCode(int i, String s) {
        this.code = i;
        this.message = s;
    }
}
