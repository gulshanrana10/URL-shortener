package com.example.UrlShortener.api;

import com.example.UrlShortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {

    private final UrlService service;

    public RedirectController(UrlService service) {
        this.service = service;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> redirect(@PathVariable String code) {
        return service.lookupActive(code)
                .map(url -> {
                    service.registerHit(url);
                    return ResponseEntity.status(302)
                            .header(HttpHeaders.LOCATION, url.getTargetUrl())
                            .build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}