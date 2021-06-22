package de.nicolasschlecker.vv.aktor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ShutterController {
    private final Logger logger = LoggerFactory.getLogger(ShutterController.class);

    @PostMapping(value = "/shutter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateShutterStatus(@RequestParam("status") String status) {
        logger.info("POST /api/v1/shutter?status={}", status);
        return ResponseEntity.noContent().build();
    }

}
