package com.harishs461.URLShortner.controller;

import com.harishs461.URLShortner.model.UrlShortenerRequest;
import com.harishs461.URLShortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/create")
    public String shortenUrl(@RequestBody UrlShortenerRequest urlShortenerRequest) {

        return urlShortenerService.longToShort(urlShortenerRequest.getLongUrl());
    }

}
