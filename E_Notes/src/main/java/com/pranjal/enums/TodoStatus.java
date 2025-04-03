package com.pranjal.enums;


import lombok.Getter;

@Getter
public enum TodoStatus {
    NOT_STARTED(1,"Not Started"), IN_PROGRESS(2, "In Progress"), COMPLETED(3, "Completed");

    private Integer id;
    private String name;

    TodoStatus(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
