package com.harishs461.URLShortner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMappingRequest {

    private String longUrl;
    private String shortUrl;
}
