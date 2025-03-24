package org.example.linkshortener.controller;

import org.example.linkshortener.dto.LinkDto;
import org.example.linkshortener.entity.OriginalLink;
import org.example.linkshortener.mapper.ConverterForOriginalLink;
import org.example.linkshortener.service.GetterOriginalLinkService;
import org.example.linkshortener.service.GetterShortedLinkService;
import org.example.linkshortener.validation.LinkValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Started tests for validated controllers rest api")
public class MainShorterLinkControllerTest {
    @Mock
    LinkValidator validator;
    @Mock
    GetterOriginalLinkService getterOriginalLinkService;
    @Mock
    GetterShortedLinkService getterShortedLinkService;
    @Mock
    ConverterForOriginalLink converter;
    @InjectMocks
    MainShorterLinkController controller;

    @Test
    @DisplayName("Test for method controller: getShortLink")
    void testGetShortLink_returnResponseEntity(){
        String originalUrl = "https://habr.com/ru/articles/743862/";
        LinkDto linkDto = new LinkDto(originalUrl);
        OriginalLink originalLink = new OriginalLink(originalUrl);
        when(converter.convertToOriginalLink(linkDto))
                .thenReturn(originalLink);
        when(getterShortedLinkService.getShortedLink(originalUrl))
                .thenReturn("http://localhost:8080/" + "4li96dqw");
        var result = controller.getShortLink(linkDto);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
        assertEquals(result.getBody(), "http://localhost:8080/4li96dqw");
        verify(validator).validate(originalUrl);
        verify(converter).convertToOriginalLink(linkDto);
    }

    @Test
    @DisplayName("Test for method controller: redirect")
    void testRedirect_returnResponseEntity(){
        String shortCode = "4li96dqw";
        when(getterOriginalLinkService.getOriginalLink(shortCode))
                .thenReturn("https://habr.com/ru/articles/743862/");
        var result = controller.redirect(shortCode);
        assertEquals(result.getStatusCode(), HttpStatus.FOUND);
        assertEquals(result.getHeaders().getLocation(), URI.create("https://habr.com/ru/articles/743862/"));
        verify(getterOriginalLinkService).getOriginalLink(shortCode);
    }
}
