package org.example.linkshortener.dto;

import org.hibernate.validator.constraints.URL;

public class LinkDto {
    @URL
    private String url;

    public LinkDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkDto(){}
}
