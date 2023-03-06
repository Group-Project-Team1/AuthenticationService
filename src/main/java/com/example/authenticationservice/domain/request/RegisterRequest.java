package com.example.authenticationservice.domain.request;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String token;
}
