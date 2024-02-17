package com.traverse.storage.utils.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ApiException {
    private final String message;
    private final ZonedDateTime timestamp;
    private final int httpStatusCode;
}
