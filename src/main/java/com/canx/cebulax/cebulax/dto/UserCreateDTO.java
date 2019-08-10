package com.canx.cebulax.cebulax.dto;

import javax.validation.constraints.NotEmpty;

public class UserCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
