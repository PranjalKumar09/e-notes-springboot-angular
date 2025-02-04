package com.safety.dto;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@MappedSuperclass
public class BadgeDto {
    private Integer id;
    private String badgeName;
    private String description;
    private String iconUrl;
    private List<Integer> userIds;

}
