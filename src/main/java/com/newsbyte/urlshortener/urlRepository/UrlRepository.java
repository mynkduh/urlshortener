package com.newsbyte.urlshortener.urlRepository;

import com.newsbyte.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}

