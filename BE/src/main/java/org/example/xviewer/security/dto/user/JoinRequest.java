package org.example.xviewer.security.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class JoinRequest {
    private String username;
    private String password;
    private String email;
}
