package com.canx.cebulax.cebulax.dto;

import javax.validation.constraints.NotEmpty;

public class GroupCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String secret;

    public GroupCreateDTO() {
    }

    public GroupCreateDTO(@NotEmpty String name, @NotEmpty String secret) {
        this.name = name;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
