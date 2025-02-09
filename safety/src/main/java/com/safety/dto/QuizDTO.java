package com.safety.dto;


import com.safety.enitity.User;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class QuizDTO {
    private Integer id;
    private String title;
    private String difficultyLevel;
    private Integer createdBy;


}
