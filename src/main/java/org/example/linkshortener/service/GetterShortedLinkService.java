package org.example.linkshortener.service;

import org.example.linkshortener.entity.OriginalLink;
import org.example.linkshortener.entity.ShortedLink;
import org.example.linkshortener.exception.LinkNotFoundException;
import org.example.linkshortener.repository.OriginalLinkRepository;
import org.example.linkshortener.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class GetterShortedLinkService {
    @Value("${message.link.not_found.404}")
    private String LINK_NOT_FOUND_MESSAGE;
    private final ShortedLinkRepository shortedLinkRepository;
    private final OriginalLinkRepository originalLinkRepository;
    private final GenerateShortLinkService generateShortLinkService;

    @Autowired
    public GetterShortedLinkService(ShortedLinkRepository shortedLinkRepository,
                                    OriginalLinkRepository originalLinkRepository,
                                    GenerateShortLinkService generateShortLinkService) {
        this.shortedLinkRepository = shortedLinkRepository;
        this.originalLinkRepository = originalLinkRepository;
        this.generateShortLinkService = generateShortLinkService;
    }

    @Transactional
    public String getShortedLink(String originalLinkUrl){
        Optional<OriginalLink> origLinkOptional = originalLinkRepository.findByUrl(originalLinkUrl);
        Date dateOfCreation = new Date();
        String shortUrl = "http://localhost:8080/" + generateShortLinkService.generateShortLink();
        if (origLinkOptional.isEmpty()){
            OriginalLink original = new OriginalLink(originalLinkUrl);
            original.setDateOfCreation(dateOfCreation);
            original.setShortAnalogue(new ArrayList());
            originalLinkRepository.save(original);
        }
        ShortedLink shortedLink = new ShortedLink(shortUrl);
        shortedLink.setDateOfCreation(dateOfCreation);
        shortedLink.setActive(true);
        ShortedLink shortenLink = shortedLinkRepository.save(shortedLink);
        originalLinkRepository.findByUrl(originalLinkUrl)
                .orElseThrow(() -> new LinkNotFoundException(LINK_NOT_FOUND_MESSAGE))
                .addShortAnalogue(shortenLink);
        return shortUrl;
    }
}
