package com.example.authenticationservice.domain.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="RegistrationToken")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name="expiration_date")
    private Timestamp expirationDate;

    @ManyToOne
    @JoinColumn(name="create_by", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
