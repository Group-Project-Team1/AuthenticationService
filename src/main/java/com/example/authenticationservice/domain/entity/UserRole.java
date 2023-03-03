package com.example.authenticationservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="UserRole")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name="create_date")
    private Timestamp createDate;

    @Column(name="last_modification_date")
    private Timestamp lastModificationDate;

    @Column(name="active_flag")
    private Boolean activeFlag;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private Role role;
}
