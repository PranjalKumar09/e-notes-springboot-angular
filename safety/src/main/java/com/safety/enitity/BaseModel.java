package com.safety.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@ToString
public abstract class BaseModel {
    @CreatedDate
    @Column(updatable = false)
    private Date createdOn;


    @LastModifiedDate
    @Column(insertable  = false)
    private Date updateOn;
}
