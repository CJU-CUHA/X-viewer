package org.example.xviewer.controller;

import org.example.xviewer.security.dto.ResMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LogsController {

    @PostMapping("/search")
    public ResponseEntity<ResMessage> searchCase(@RequestParam String keyword) {
        return null;
    }

    @GetMapping("/proc")
    public ResponseEntity<ResMessage> proc(@RequestParam Long id) {
        return null;
    }
    @GetMapping("/mapping")
    public ResponseEntity<ResMessage> mapping(@RequestParam Long id) {
        return null;
    }
}
