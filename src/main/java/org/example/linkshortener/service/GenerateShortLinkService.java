package org.example.linkshortener.service;

import org.example.linkshortener.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateShortLinkService {
    private final ShortedLinkRepository shortedLinkRepository;
    private static final String alphabet = "qwertyuiopasdfghjklzxcvbnm0123456789";

    @Autowired
    public GenerateShortLinkService(ShortedLinkRepository shortedLinkRepository) {
        this.shortedLinkRepository = shortedLinkRepository;
    }

    public String generateShortLink(){
        String[] elems = alphabet.split("");
        String shortUrl = "";
        for (int i = 0; i < 8; i++){
            shortUrl = shortUrl + elems[new Random().nextInt(elems.length)];
        }
        if (shortedLinkRepository.findByUrl(shortUrl).isPresent()){
            generateShortLink();
        }
        return shortUrl;
    }


}
