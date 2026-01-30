package com.gustavo.ticketing.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class PingController {

  @GetMapping("/api/v1/ping")
  public Map<String, Object> ping() {
    return Map.of(
        "status", "ok",
        "ts", Instant.now().toString()
    );
  }
}
