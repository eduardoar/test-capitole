package com.rosales.springtest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrice;

    private Long idBrand;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long idPriceRate;

    private Long idProduct;

    private Integer priority;

    private BigDecimal price;

    private String currency;

}
