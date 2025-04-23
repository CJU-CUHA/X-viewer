package org.example.xviewer.security.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
