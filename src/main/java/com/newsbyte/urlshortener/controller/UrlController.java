package com.newsbyte.urlshortener.controller;

import com.newsbyte.urlshortener.entity.Url;
import com.newsbyte.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        String hash = urlService.shortenUrl(longUrl);
        return ResponseEntity.ok("http://newsbytesapp.com/" + hash);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String hash) {
        Optional<Url> urlOpt = urlService.findOriginalUrl(hash);
        if (urlOpt.isPresent()) {
            Url url = urlOpt.get();
            urlService.incrementClickCount(url);
            return ResponseEntity.status(302).location(URI.create(url.getLongUrl())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
