package com.safety.dto;

import java.util.List;

public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer organizationId;
    private List<Integer> quizAttemptsIds;
    private List<Integer> badgeIds;
}
