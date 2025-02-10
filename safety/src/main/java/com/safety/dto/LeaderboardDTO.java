package com.safety.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class LeaderboardDTO {
    private Integer id;
    private Integer totalScore;
    private Integer userId;
    private Integer rank;
}
