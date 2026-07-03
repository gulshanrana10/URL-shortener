package com.example.UrlShortener.api;

import com.example.UrlShortener.request.CreateShortUrlRequest;
import com.example.UrlShortener.repository.ShortUrl;
import com.example.UrlShortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShortUrl> create(@Valid @RequestBody CreateShortUrlRequest request) {
        ShortUrl result = service.create(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrl> get(@PathVariable String code) {
        return service.lookupActive(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
