package com.example.authenticationservice.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
}
