package com.canx.cebulax.cebulax.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GroupCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String secret;

    @NotNull
    private Long createdBy;

    public GroupCreateDTO() {
    }

    public GroupCreateDTO(@NotEmpty String name, @NotEmpty String secret, @NotNull Long createdBy) {
        this.name = name;
        this.secret = secret;
        this.createdBy = createdBy;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
