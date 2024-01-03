package com.harishs461.URLShortner.service;

import com.harishs461.URLShortner.exception.MalformedUrlException;
import com.harishs461.URLShortner.exception.ResourceNotFoundException;
import com.harishs461.URLShortner.model.UrlMapping;
import com.harishs461.URLShortner.repository.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class UrlShortenerService {


    private final String elements;
    private final UrlShortenerRepository  urlShortenerRepository;

    @Autowired
    UrlShortenerService(UrlShortenerRepository urlShortenerRepository) {
        elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.urlShortenerRepository = urlShortenerRepository;
    }
    public String longToShort(String longUrl) throws MalformedUrlException {

        UrlMapping urlMappingInDb = urlShortenerRepository.findByLongUrl(longUrl);
        if(null != urlMappingInDb) {
            return urlMappingInDb.getShortUrl();
        }

        long counter = System.currentTimeMillis();
        String counterInBase62 = convertBase10ToBase62(counter);
        String shortUrl;

        try{
            URL url  = new URL(longUrl);
            shortUrl = url.getProtocol() + "://" + url.getHost() +
                    (url.getPort() != -1 ? ":" + url.getPort() : "") + "/" +
                    counterInBase62 +
                    (url.getQuery() != null ? "?" + url.getQuery() : "") +
                    (url.getRef() != null ? "#" + url.getRef() : "");
        }catch (MalformedURLException malformedURLException) {
            throw new MalformedUrlException(malformedURLException.getMessage());
        }

        UrlMapping urlMapping = UrlMapping.builder()
                .id(counter)
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();
        urlShortenerRepository.save(urlMapping);
        return shortUrl;
    }

    public String shortToLong(String shortUrl) {

        Long id = extractIdFromShortUrl(shortUrl);
        String longUrl;
        if (id == null) throw new ResourceNotFoundException("No long url mapping found for the provided short url.");
        else {
            Optional<UrlMapping> urlMapping = urlShortenerRepository.findById(id);
            longUrl = urlMapping.map(UrlMapping::getLongUrl).orElse(null);
            if(null == longUrl)
                throw new ResourceNotFoundException("No long url mapping found for the provided short url.");
        }

        return longUrl;
    }

    private Long extractIdFromShortUrl(String shortUrl) throws MalformedUrlException{
        long id;
        try {
            URL url  = new URL(shortUrl);
            System.out.println(url.getPath());
            String sUrl = url.getPath().replaceFirst("/","");
            id = convertBase62ToBase10(sUrl);
        }catch(MalformedURLException malformedURLException) {
           throw new MalformedUrlException(malformedURLException.getMessage());
        }

        return id;
    }

    private String convertBase10ToBase62(long counter) {
        StringBuilder sb = new StringBuilder();

        while(counter != 0) {
            sb.insert(0, elements.charAt((int) (counter%62)));
            counter = counter / 62;
        }

        while (sb.length() != 7) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    private long convertBase62ToBase10(String base62) {
        long base10 = 0;
        int length = base62.length();

        for (int i = 0; i < length; i++) {
            int charIndex = elements.indexOf(base62.charAt(i));
            base10 = base10 * 62 + charIndex;
        }

        return base10;
    }
}
