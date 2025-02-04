package com.safety.dto;

public class OrganizationDTO {
    private Integer id;
    private String organizationName;
    // List of user IDs that belong to this organization.
    private List<Integer> userIds;
