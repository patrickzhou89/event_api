package com.plan.code.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data
class CreateEventDTO {
    @NotNull
    private String name;

    @NotNull
    private Boolean published;
}
