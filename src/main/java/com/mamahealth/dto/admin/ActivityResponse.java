package com.mamahealth.dto.admin;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityResponse {

    private String activity;

    private String performedBy;

    private LocalDateTime createdAt;

}