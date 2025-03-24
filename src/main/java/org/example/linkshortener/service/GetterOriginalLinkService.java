package org.example.linkshortener.service;

import org.example.linkshortener.entity.ShortedLink;
import org.example.linkshortener.exception.DeactivationLinkException;
import org.example.linkshortener.exception.LinkNotFoundException;
import org.example.linkshortener.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetterOriginalLinkService {
    @Value("${message.link.not_found.404}")
    private String LINK_NOT_FOUND_MESSAGE;
    @Value("${message.link.deactivation}")
    private String LINK_DEACTIVATION_MESSAGE;

    private final ShortedLinkRepository shortedLinkRepository;

    @Autowired
    public GetterOriginalLinkService(ShortedLinkRepository shortedLinkRepository) {
        this.shortedLinkRepository = shortedLinkRepository;
    }

    @Transactional
    public String getOriginalLink(String shortCode){
        String shortLink = "http://localhost:8080/" + shortCode;
        Optional<ShortedLink> shortLinkOptional = shortedLinkRepository.findByUrl(shortLink);
        if (shortLinkOptional.isPresent()){
            ShortedLink link = shortLinkOptional.get();
            if (link.isActive()){
                return link
                        .getOriginal()
                        .getUrl();
            } else {
                throw new DeactivationLinkException(LINK_DEACTIVATION_MESSAGE);
            }
        } else {
            throw new LinkNotFoundException(LINK_NOT_FOUND_MESSAGE);
        }
    }
}
