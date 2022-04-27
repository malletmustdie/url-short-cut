package ru.elias.urlshortcut.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.elias.urlshortcut.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByEncodedUrl(String encodedUrl);

    @Modifying(flushAutomatically = true)
    @Query(value = "update Link set total = total + 1 where id = :id")
    void incrementTotalColumn(@Param("id") Long id);

}
