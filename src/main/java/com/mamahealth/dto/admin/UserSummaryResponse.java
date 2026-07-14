package com.mamahealth.dto.admin;

import com.mamahealth.entity.Role;

import lombok.Data;

@Data
public class UserSummaryResponse {

    private Long id;
    private String email;
    private Role role;
    private Boolean active;

   
}