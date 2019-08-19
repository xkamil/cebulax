package com.canx.cebulax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateDTO {

    @NotNull
    private Long resourceId;

    @NotNull
    private LocalDateTime dateTo;
}
