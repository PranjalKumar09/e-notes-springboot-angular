package com.pranjal.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;

    private Integer createdBy;
    private Date createdOn;
    private Integer updateBy;
    private Date updateOn;

    private Boolean isDeleted;
    private LocalDateTime deletedAt;

    private FilesDto fileDetails;



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto{
        private Integer id;
        private String name;
    };

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilesDto{
        private Integer id;
        private String originalFileName;
        private String displayFileName;
    };
}
