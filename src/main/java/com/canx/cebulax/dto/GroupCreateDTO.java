package com.canx.cebulax.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GroupCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String secret;

}
