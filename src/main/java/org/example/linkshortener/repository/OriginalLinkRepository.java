package org.example.linkshortener.repository;

import org.example.linkshortener.entity.OriginalLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginalLinkRepository extends JpaRepository<OriginalLink, Long> {

    Optional<OriginalLink> findByUrl(String url);
}
