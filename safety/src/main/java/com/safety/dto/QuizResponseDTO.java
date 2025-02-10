package com.safety.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class QuizResponseDTO {
    private Integer id;
    private Boolean isCorrect;
    private Integer quizAttemptId;
    private Integer questionId;
}
