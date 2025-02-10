package com.safety.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@MappedSuperclass
public class QuizAttemptDTO {
    private Integer id;
    private Date attemptDate;
    private Integer score;
    private Integer userId;
    private Integer quizId;
    private List<Integer> responseIds;
}
