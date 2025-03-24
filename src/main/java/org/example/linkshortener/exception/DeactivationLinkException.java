package org.example.linkshortener.exception;

public class DeactivationLinkException extends RuntimeException{
    public DeactivationLinkException(String msg){
        super(msg);
    }
}
