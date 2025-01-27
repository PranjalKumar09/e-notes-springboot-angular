package com.pranjal.enitity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@ToString
public class BaseModel {
    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Date createdOn;
    private Integer updateBy;
    private Date updateOn;
}
