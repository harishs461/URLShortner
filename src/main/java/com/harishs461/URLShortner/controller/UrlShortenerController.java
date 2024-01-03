package com.harishs461.URLShortner.controller;

import com.harishs461.URLShortner.exception.MalformedUrlException;
import com.harishs461.URLShortner.model.UrlMappingRequest;
import com.harishs461.URLShortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/create")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlMappingRequest urlMappingRequest) throws MalformedUrlException {
        String shortUrl = urlShortenerService.longToShort(urlMappingRequest.getLongUrl());
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping("/fetchLongUrl")
    public ResponseEntity<?> fetchLongUrl(@RequestBody UrlMappingRequest urlMappingRequest) {
        String longUrl = urlShortenerService.shortToLong(urlMappingRequest.getShortUrl());
        return new ResponseEntity<>(longUrl,HttpStatus.OK);
    }
}
