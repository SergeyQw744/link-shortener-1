package org.example.linkshortener.service;

import org.example.linkshortener.entity.ShortedLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeactivationShortLinkService {
    private final ShortedLinkService shortedLinkService;

    @Autowired
    public DeactivationShortLinkService(ShortedLinkService shortedLinkService) {
        this.shortedLinkService = shortedLinkService;
    }


    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void deactivationShortLinksTask(){
        List<ShortedLink> links = shortedLinkService.findActiveLinks();
        if (!links.isEmpty()){
            links.stream()
                    .filter(link -> link.getDateOfCreation().getTime() + 120000 <= new Date().getTime())
                    .forEach(link -> shortedLinkService.deactivation(link.getId()));
        }
    }
}
