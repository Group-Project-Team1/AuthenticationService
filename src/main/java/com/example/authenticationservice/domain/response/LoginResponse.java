package com.example.authenticationservice.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
    private String message;
    private String token;

}
