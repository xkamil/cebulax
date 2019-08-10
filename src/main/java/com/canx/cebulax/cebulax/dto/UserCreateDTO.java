package com.canx.cebulax.cebulax.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserCreateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String familyName;

    @NotNull
    private Boolean createFamily;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String name, String password, String familyName, Boolean createFamily) {
        this.name = name;
        this.password = password;
        this.familyName = familyName;
        this.createFamily = createFamily;
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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Boolean getCreateFamily() {
        return createFamily;
    }

    public void setCreateFamily(Boolean createFamily) {
        this.createFamily = createFamily;
    }
}