package org.example.linkshortener.repository;

import org.example.linkshortener.entity.ShortedLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortedLinkRepository extends JpaRepository<ShortedLink, Integer> {

     @Transactional
     Optional<ShortedLink> findByUrl(String url);

     @Modifying
     @Query("update ShortedLink s set s.isActive = false where s.id = ?1")
     void deactivation(int id);

     @Query("select s from ShortedLink s where s.isActive = true")
     List<ShortedLink> findActiveLinks();
}
