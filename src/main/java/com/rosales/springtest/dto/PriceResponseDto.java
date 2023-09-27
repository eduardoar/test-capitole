package com.rosales.springtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceResponseDto {

    private Long idProduct;

    private Integer idBrand;

    private Long idPriceRate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;

}
