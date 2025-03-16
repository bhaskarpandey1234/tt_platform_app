package com.project.irctc.exception;

public class JwtSignatureException extends RuntimeException {

    public JwtSignatureException(String message) {
        super(message);
    }

    public JwtSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
