package com.example.authenticationservice.domain.response;

import com.example.authenticationservice.domain.entity.User;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterResponse {
    private String message;
    private User user;
}
