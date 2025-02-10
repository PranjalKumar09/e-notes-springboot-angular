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
@EntityListeners(AuditingEntityListener.class)


@Table(name = "badges")
public class Badge extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    @ManyToMany(mappedBy = "badges")
    private List<User> users;
}
