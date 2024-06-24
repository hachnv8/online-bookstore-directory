package com.learnmicroservice.exception;

import java.io.Serial;

public class SQLException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
