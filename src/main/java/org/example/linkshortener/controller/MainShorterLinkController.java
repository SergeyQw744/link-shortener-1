package org.example.linkshortener.controller;

import org.example.linkshortener.dto.LinkDto;
import org.example.linkshortener.mapper.ConverterForOriginalLink;
import org.example.linkshortener.service.GetterOriginalLinkService;
import org.example.linkshortener.service.GetterShortedLinkService;
import org.example.linkshortener.validation.LinkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MainShorterLinkController {
    private final LinkValidator linkValidator;
    private final GetterOriginalLinkService getterOriginalLinkService;
    private final GetterShortedLinkService getterShortedLinkService;
    private final ConverterForOriginalLink converterForOriginalLink;

    @Autowired
    public MainShorterLinkController(GetterOriginalLinkService getterOriginalLinkService,
                                     GetterShortedLinkService getterShortedLinkService,
                                     LinkValidator linkValidator,
                                     ConverterForOriginalLink converterForOriginalLink) {
        this.getterOriginalLinkService = getterOriginalLinkService;
        this.getterShortedLinkService = getterShortedLinkService;
        this.linkValidator = linkValidator;
        this.converterForOriginalLink = converterForOriginalLink;
    }

    @PostMapping("/short") //убрать /short
    public ResponseEntity<String> getShortLink(@RequestBody @Validated LinkDto originalLinkUrl){
        linkValidator.validate(converterForOriginalLink.convertToOriginalLink(originalLinkUrl).getUrl());
        String shortUrl = getterShortedLinkService.getShortedLink(originalLinkUrl.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shortUrl);
    }

    @GetMapping("/redirect/{short_code}")  //убрать /redirect
    public ResponseEntity<Void> redirect(@PathVariable("short_code") String shortCode){
        String originalLinkUrl = getterOriginalLinkService.getOriginalLink(shortCode);
        return  ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalLinkUrl))
                .build();
    }
}
