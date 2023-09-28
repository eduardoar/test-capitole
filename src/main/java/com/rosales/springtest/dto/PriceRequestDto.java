package com.rosales.springtest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceRequestDto {

    @NotNull
    private LocalDateTime applicationDate;

    @NotNull
    @Min(1)
    private Long idProduct;

    @NotNull
    @Min(1)
    private Long idBrand;

}
