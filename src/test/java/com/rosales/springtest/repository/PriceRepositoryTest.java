package com.rosales.springtest.repository;

import com.rosales.springtest.exception.ModelNotFoundException;
import com.rosales.springtest.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PriceRepositoryTest {

    private final IPriceRepository priceRepository;

    private static final Long VALID_PRODUCT_ID = 35455L;
    private static final Long INVALID_PRODUCT_ID = 35456L;
    private static final Long VALID_BRAND_ID = 1L;
    @Autowired
    public PriceRepositoryTest(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Test
    public void shouldFindPriceForValidInput() {

        //given
        String valueDate = "2020-06-14 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime applicationDate = LocalDateTime.parse(valueDate, formatter);
        Long idProduct = VALID_PRODUCT_ID;
        Long idBrand = VALID_BRAND_ID;

        //when
        Price priceRequest = priceRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
                applicationDate, applicationDate, idProduct, idBrand )
                .orElseThrow(() -> new ModelNotFoundException("No records found"));

        //then
        assertThat(priceRequest).isNotNull();

    }

    @Test
    public void shouldThrowExceptionForInvalidInput() {

        //given
        String valueDate = "2020-06-14 10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime applicationDate = LocalDateTime.parse(valueDate, formatter);
        Long idProduct = INVALID_PRODUCT_ID;
        Long idBrand = VALID_BRAND_ID;

        //then
        assertThrows(ModelNotFoundException.class, () -> {
            priceRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
                            applicationDate, applicationDate, idProduct, idBrand )
                    .orElseThrow(() -> new ModelNotFoundException("No records found"));
        });

    }


}
