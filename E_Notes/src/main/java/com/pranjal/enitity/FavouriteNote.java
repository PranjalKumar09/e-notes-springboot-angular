package com.pranjal.enitity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FavouriteNote {
        @Id
        @GeneratedValue (strategy =  GenerationType.IDENTITY)
        private Integer id;
        @ManyToOne
        private Notes notes;
        private Integer userId;
}
