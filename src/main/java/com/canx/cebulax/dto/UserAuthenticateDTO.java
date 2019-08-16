package com.canx.cebulax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

}
