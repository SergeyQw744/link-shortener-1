package org.example.linkshortener.exception;

public class InvalidLinkException extends RuntimeException {
    public InvalidLinkException(String msg) {
        super(msg);
    }
}
