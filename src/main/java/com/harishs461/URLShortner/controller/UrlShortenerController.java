package com.harishs461.URLShortner.controller;

import com.harishs461.URLShortner.model.UrlMappingRequest;
import com.harishs461.URLShortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/create")
    public String shortenUrl(@RequestBody UrlMappingRequest urlMappingRequest) {
        return urlShortenerService.longToShort(urlMappingRequest.getLongUrl());
    }

    @GetMapping("/fetchLongUrl")
    public String fetchLongUrl(@RequestBody UrlMappingRequest urlMappingRequest) {
        return urlShortenerService.shortToLong(urlMappingRequest.getShortUrl());
    }
}
