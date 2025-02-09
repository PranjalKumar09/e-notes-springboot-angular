package com.safety.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class QuizQuestionDTO {
    private Integer id;
    private String questionText;
    private String answerOptions;
    private String correctAnswer;
    private String feedback;
    private Integer quizId;
}
