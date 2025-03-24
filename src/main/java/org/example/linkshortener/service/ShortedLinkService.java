package org.example.linkshortener.service;

import org.example.linkshortener.entity.ShortedLink;
import org.example.linkshortener.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShortedLinkService {
    private final ShortedLinkRepository shortedLinkRepository;

    @Autowired
    public ShortedLinkService(ShortedLinkRepository shortedLinkRepository) {
        this.shortedLinkRepository = shortedLinkRepository;
    }

    public List<ShortedLink> findActiveLinks(){
        return shortedLinkRepository.findActiveLinks();
    }

    @Transactional
    public void deactivation(int id){
        shortedLinkRepository.deactivation(id);
    }
}
