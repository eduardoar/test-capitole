package com.rosales.springtest.service;

import com.rosales.springtest.dto.PriceRequestDto;
import com.rosales.springtest.dto.PriceResponseDto;
import com.rosales.springtest.exception.ModelNotFoundException;
import com.rosales.springtest.model.Price;
import com.rosales.springtest.repository.IPriceRepository;
import com.rosales.springtest.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private IPriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    private Price price;

    private static final LocalDateTime VALID_APPLICATION_DATE = LocalDateTime.parse("2020-06-14T10:00:00");
    private static final Long VALID_PRODUCT_ID = 35455L;
    private static final Long INVALID_PRODUCT_ID = 35456L;
    private static final Long VALID_BRAND_ID = 1L;

    @BeforeEach
    void setup() {
        price = new Price();
        price.setIdPrice(1L);
        price.setIdBrand(1L);
        price.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        price.setEndDate(LocalDateTime.parse("2020-06-14T18:00:00"));
        price.setIdPriceRate(1L);
        price.setIdProduct(35455L);
        price.setPriority(0);
        price.setPrice(BigDecimal.valueOf(38.95));
        price.setCurrency("EUR");
    }

    @Test
    public void shouldFindPriceForValidInput() {
        //given
        PriceRequestDto validPriceRequestDto = createValidPriceRequestDto();
        mockPriceRepositoryToReturnPrice();

        //when
        PriceResponseDto priceResponseDto = priceService.findPrice(validPriceRequestDto);

        //then
        assertThat(priceResponseDto).isNotNull();
    }

    @Test
    public void shouldThrowExceptionForInvalidInput() {
        //given
        PriceRequestDto invalidPriceRequestDto = createInvalidPriceRequestDto();
        mockPriceRepositoryToReturnEmptyOptional();

        //then
        assertThrows(ModelNotFoundException.class, () -> {
            priceService.findPrice(invalidPriceRequestDto);
        });
    }

    private PriceRequestDto createValidPriceRequestDto() {
        PriceRequestDto validPriceRequestDto = new PriceRequestDto();
        validPriceRequestDto.setApplicationDate(VALID_APPLICATION_DATE);
        validPriceRequestDto.setIdProduct(VALID_PRODUCT_ID);
        validPriceRequestDto.setIdBrand(VALID_BRAND_ID);
        return validPriceRequestDto;
    }

    private PriceRequestDto createInvalidPriceRequestDto() {
        PriceRequestDto invalidPriceRequestDto = new PriceRequestDto();
        invalidPriceRequestDto.setApplicationDate(VALID_APPLICATION_DATE);
        invalidPriceRequestDto.setIdProduct(INVALID_PRODUCT_ID);
        invalidPriceRequestDto.setIdBrand(VALID_BRAND_ID);
        return invalidPriceRequestDto;
    }

    private void mockPriceRepositoryToReturnPrice() {
        given(priceRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
                VALID_APPLICATION_DATE,
                VALID_APPLICATION_DATE,
                VALID_PRODUCT_ID,
                VALID_BRAND_ID))
                .willReturn(Optional.ofNullable(price));
    }

    private void mockPriceRepositoryToReturnEmptyOptional() {
        given(priceRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
                VALID_APPLICATION_DATE,
                VALID_APPLICATION_DATE,
                INVALID_PRODUCT_ID,
                VALID_BRAND_ID))
                .willReturn(Optional.empty());
    }

}
