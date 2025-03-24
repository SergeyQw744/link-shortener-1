package org.example.linkshortener.exception;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException(String msg){
        super(msg);
    }
}
