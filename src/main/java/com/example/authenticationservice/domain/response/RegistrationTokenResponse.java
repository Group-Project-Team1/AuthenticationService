package com.example.authenticationservice.domain.response;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationTokenResponse {
    private String message;
    private RegistrationToken registrationToken;
}
