package com.canx.cebulax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCreateDTO {

    @NotEmpty
    private String name;

    @Size(min = 1, max = 100)
    private Integer slots;
}
