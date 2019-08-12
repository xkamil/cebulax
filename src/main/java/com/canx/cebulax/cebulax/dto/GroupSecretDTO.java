package com.canx.cebulax.cebulax.dto;

import javax.validation.constraints.NotEmpty;

public class GroupSecretDTO {

    @NotEmpty
    private String secret;

    public GroupSecretDTO(String secret) {
        this.secret = secret;
    }

    public GroupSecretDTO() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
