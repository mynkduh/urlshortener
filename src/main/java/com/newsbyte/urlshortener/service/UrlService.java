package com.newsbyte.urlshortener.service;

import com.newsbyte.urlshortener.entity.Url;
import com.newsbyte.urlshortener.urlRepository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    private final Sqids sqids;
    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.sqids = new Sqids.Builder().
                minLength(6).build();
    }

    public String shortenUrl(String longUrl) {
        // Save the original URL in the database
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setClickCount(0);
        url = urlRepository.save(url);
        return sqids.encode(List.of(url.getId()));
    }

    public Optional<Url> findOriginalUrl(String hash) {
        List<Long> ids = sqids.decode(hash);
        if (!ids.isEmpty()) {
            long id = ids.get(0);
            return urlRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    
    public void incrementClickCount(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
    }
}
