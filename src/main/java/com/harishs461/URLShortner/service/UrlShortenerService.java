package com.harishs461.URLShortner.service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Service
public class UrlShortenerService {


    private Integer counter;
    private final String elements;
    private HashMap<String,Integer> ltos;
    private HashMap<Integer,String> stol;

    UrlShortenerService() {
        counter = 1000000000;
        elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ltos = new HashMap<>();
        stol = new HashMap<>();
    }
    public String longToShort(String longUrl) {

        if(ltos.containsKey(longUrl)) {
            return stol.get(ltos.get(longUrl));
        }

        String counterInBase62 = convertBase10ToBase62(counter);
        String shortUrl;

        try {
            URL url  = new URL(longUrl);
            shortUrl = url.getProtocol() + "://" + url.getHost() +
                    (url.getPort() != -1 ? ":" + url.getPort() : "") + "/" +
                    counterInBase62 +
                    (url.getQuery() != null ? "?" + url.getQuery() : "") +
                    (url.getRef() != null ? "#" + url.getRef() : "");
        }catch(MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
            return longUrl;
        }
        ltos.put(longUrl,counter);
        stol.put(counter,shortUrl);
        counter++;
        return shortUrl;
    }

    private String convertBase10ToBase62(int counter) {
        StringBuilder sb = new StringBuilder();

        while(counter != 0) {
            sb.insert(0, elements.charAt(counter%62));
            counter = counter / 62;
        }

        while (sb.length() != 7) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }
}
