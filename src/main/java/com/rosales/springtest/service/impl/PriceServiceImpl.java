package com.rosales.springtest.service.impl;

import com.rosales.springtest.dto.PriceRequestDto;
import com.rosales.springtest.dto.PriceResponseDto;
import com.rosales.springtest.exception.ModelNotFoundException;
import com.rosales.springtest.model.Price;
import com.rosales.springtest.repository.IGenericRepository;
import com.rosales.springtest.repository.IPriceRepository;
import com.rosales.springtest.service.IPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceServiceImpl extends CRUDImpl<Price, Long> implements IPriceService {

    private final IPriceRepository priceRepository;

    @Override
    protected IGenericRepository<Price, Long> getRepo() {
        return priceRepository;
    }

    @Override
    public PriceResponseDto findPrice(PriceRequestDto priceRequestDto) {

        log.info("PriceService::findPrice method executed");

        PriceResponseDto priceResponseDTO = new PriceResponseDto();

        Price price = findPriceWithParameters(
                priceRequestDto.getApplicationDate(),
                priceRequestDto.getIdProduct(),
                priceRequestDto.getIdBrand())
                .orElseThrow(() -> new ModelNotFoundException("No records found"));;

        BeanUtils.copyProperties(price, priceResponseDTO);

        return priceResponseDTO;
    }

    private Optional<Price> findPriceWithParameters(LocalDateTime applicationDate, Long idProduct, Long idBrand) {
        return priceRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdProductAndIdBrandOrderByPriorityDesc(
                applicationDate,
                applicationDate,
                idProduct,
                idBrand
        );
    }

}
