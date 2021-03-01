package com.plan.code.dto;

import lombok.Data;

public @Data class  EventDTO {
    private int id;

    private String name;

    private Boolean published;

    private String createdAt;
}
