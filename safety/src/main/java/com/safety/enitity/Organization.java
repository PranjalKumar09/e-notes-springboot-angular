package com.safety.enitity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)@Table(name = "organizations")
public class Organization extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "org_name", nullable = false, unique = true)
    private String organizationName;

    @OneToMany(mappedBy = "organization")
    private List<User> users;
}
