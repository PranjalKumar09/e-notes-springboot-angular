package com.pranjal.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Integer id;
    private String title;
    private StatusDto status;


    private Integer createdBy;
    private Date createdOn;
    private Integer updateBy;
    private Date updateOn;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class StatusDto {
        private Integer id;
        private String name;
    }
}

