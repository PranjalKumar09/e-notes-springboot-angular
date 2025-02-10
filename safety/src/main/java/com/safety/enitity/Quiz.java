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
@Table(name = "quizzes")
public class Quiz extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quiz_title", nullable = false)
    private String title;

    @Column(name = "difficulty_level")
    private String difficultyLevel; // e.g., "Easy", "Medium", "Hard"


    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;


    @OneToMany(mappedBy = "quiz")
    private List<QuizQuestion> questions;
}
