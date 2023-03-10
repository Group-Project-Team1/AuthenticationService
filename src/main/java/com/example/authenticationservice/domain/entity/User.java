package com.example.authenticationservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="User")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="create_date")
    private Timestamp createDate;

    @Column(name="last_modification_date")
    private Timestamp lastModificationDate;

    @Column(name="active_flag")
    private Boolean activeFlag;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private Set<RegistrationToken> registrationTokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "UserRole",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    @JsonIgnore
    @ToString.Exclude
    private Set<Role> roles;
}
