package org.example.xviewer.service;

import org.example.xviewer.security.dto.ResMessage;
import org.example.xviewer.security.dto.user.JoinRequest;
import org.example.xviewer.security.dto.user.LoginRequest;

public interface UserService {
    ResMessage join(JoinRequest joinRequest);
    ResMessage login(LoginRequest loginRequest);
}
