package de.nicolasschlecker.vv.aktor.controller;

import de.nicolasschlecker.vv.aktor.domain.ShutterStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ShutterController {

    @PostMapping(value = "shutter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateShutterStatus(@RequestParam("status") ShutterStatus status) {
        // return ResponseEntity.ok(aktorService.create(aktor));
        return ResponseEntity.ok().build();
    }

}
