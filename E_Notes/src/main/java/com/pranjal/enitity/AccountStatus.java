package com.pranjal.enitity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AccountStatus {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    private Boolean isActive;
    private String verificationCode;
    private String passwordResetToken;

    @OneToOne(mappedBy = "status", optional = false)
    private User user;


}
