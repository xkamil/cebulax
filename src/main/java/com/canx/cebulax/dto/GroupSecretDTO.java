package com.canx.cebulax.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GroupSecretDTO {

    @NotEmpty
    private String secret;

}
