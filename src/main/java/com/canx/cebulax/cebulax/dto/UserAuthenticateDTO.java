package com.canx.cebulax.cebulax.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserAuthenticateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

}
