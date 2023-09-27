package com.rosales.springtest.service;

import com.rosales.springtest.dto.PriceRequestDto;
import com.rosales.springtest.dto.PriceResponseDto;
import com.rosales.springtest.model.Price;

public interface IPriceService extends ICRUDService<Price, Long>{
    PriceResponseDto findPrice(PriceRequestDto priceRequestDto);

}
