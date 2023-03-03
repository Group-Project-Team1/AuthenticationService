package com.example.authenticationservice.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name="Role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name="create_date")
    private Timestamp createDate;

    @Column(name="last_modification_date")
    private Timestamp lastModificationDate;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;
}
