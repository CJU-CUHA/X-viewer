package org.example.xviewer.controller;

import lombok.RequiredArgsConstructor;
import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.user.JoinRequest;
import org.example.xviewer.security.dto.user.LoginRequest;
import org.example.xviewer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<ResMessage> join(JoinRequest joinRequest) {
        return ResponseEntity.ok(userService.join(joinRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<ResMessage> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
