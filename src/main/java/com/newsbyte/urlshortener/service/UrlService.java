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


    // Sqids instance for encoding and decoding
    private final Sqids sqids;

    // Repository to interact with the database
    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;

        // Initialize Sqids with default settings or you can customize the alphabet if needed
        this.sqids = new Sqids.Builder().
                minLength(6).build();
    }

    /**
     * Shorten the long URL by encoding its ID using Sqids.
     *
     * @param longUrl The original long URL that needs to be shortened.
     * @return The shortened URL identifier (hash).
     */
    public String shortenUrl(String longUrl) {
        // Save the original URL in the database
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setClickCount(0);
        url = urlRepository.save(url);

        // Generate a short URL by encoding the database ID using Sqids
        return sqids.encode(List.of(url.getId()));
    }

    /**
     * Find the original URL based on the shortened URL identifier (hash).
     *
     * @param hash The shortened URL identifier (hash).
     * @return An optional containing the original URL if found.
     */
    public Optional<Url> findOriginalUrl(String hash) {
        // Decode the Sqids hash to get the original ID (as List<Long>)
        List<Long> ids = sqids.decode(hash);

        if (!ids.isEmpty()) {
            long id = ids.get(0);  // Assuming the first decoded value is the original ID
            return urlRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }


    /**
     * Increment the click count for the URL and save the updated count.
     *
     * @param url The URL entity whose click count should be incremented.
     */
    public void incrementClickCount(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
    }
}
