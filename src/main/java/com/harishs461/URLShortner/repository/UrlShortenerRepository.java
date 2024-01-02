package com.harishs461.URLShortner.repository;

import com.harishs461.URLShortner.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlMapping, Long> {

    UrlMapping findByLongUrl(String url);
}
