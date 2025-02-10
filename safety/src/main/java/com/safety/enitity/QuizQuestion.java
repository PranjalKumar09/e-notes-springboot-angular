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
@Table(name = "quiz_questions")
public class QuizQuestion extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "answer_options", columnDefinition = "TEXT")
    private String answerOptions;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
