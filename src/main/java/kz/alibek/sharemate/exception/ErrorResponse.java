package kz.alibek.sharemate.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private final String error;
    private final LocalDateTime timestamp;
    private final HttpStatus status;

    public ErrorResponse(String error, HttpStatus status) {
        this.error = error;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
