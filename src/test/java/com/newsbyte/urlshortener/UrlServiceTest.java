package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sqids.Sqids;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    private Sqids sqids;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sqids = new Sqids.Builder().build();
    }

    @Test
    void testShortenUrl() {
        // Mock the URL object to be saved
        Url url = new Url();
        url.setId(1L);
        url.setLongUrl("https//newsbyteapp.com/news");

        // When saving a new URL, return the mocked URL with ID
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        // Call the method to test
        String shortUrl = urlService.shortenUrl(url.getLongUrl());

        // Check if the returned short URL is not null and contains the encoded hash
        assertNotNull(shortUrl);
        assertEquals(sqids.encode(List.of(1L)), shortUrl); // Expected encoded ID as the hash
    }

    @Test
    void testFindOriginalUrl() {
        // Mock the URL object to be retrieved
        Url url = new Url();
        url.setId(1L);
        url.setLongUrl("https//newsbyteapp.com/news");

        // Mock the repository to return the URL when queried by ID
        when(urlRepository.findById(1L)).thenReturn(Optional.of(url));

        // Call the method to test with the encoded hash for ID 1
        Optional<Url> originalUrl = urlService.findOriginalUrl(sqids.encode(List.of(1L)));

        // Check if the original URL is returned correctly
        assertTrue(originalUrl.isPresent());
        assertEquals(url.getLongUrl(), originalUrl.get().getLongUrl());
    }
}
