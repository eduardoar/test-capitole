package com.rosales.springtest.repository;

import com.rosales.springtest.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IPriceRepository extends IGenericRepository<Price, Long> {
    Optional<Price> findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
            LocalDateTime startDate, LocalDateTime endDate, Long idProduct, Integer idBrand);

}
