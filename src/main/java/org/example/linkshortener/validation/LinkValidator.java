package org.example.linkshortener.validation;

import org.apache.commons.validator.routines.UrlValidator;
import org.example.linkshortener.exception.InvalidLinkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS;

@Component
public class LinkValidator {

    @Value("${message.invalid_link}")
    private String INVALID_LINK_MESSAGE;

    public void validate(String link){
        UrlValidator validator = new UrlValidator(ALLOW_LOCAL_URLS);
        if (!validator.isValid(link)){
            throw new InvalidLinkException(INVALID_LINK_MESSAGE);
        }
    }
}
