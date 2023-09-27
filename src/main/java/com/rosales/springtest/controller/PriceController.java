package com.rosales.springtest.controller;

import com.rosales.springtest.dto.PriceRequestDto;
import com.rosales.springtest.dto.PriceResponseDto;
import com.rosales.springtest.service.IPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final IPriceService priceService;

    @GetMapping
    public ResponseEntity<PriceResponseDto> findPrice(@Valid @ModelAttribute PriceRequestDto priceRequestDto) {
        log.info("PriceController::findPrice method executed");
        return ResponseEntity.ok(priceService.findPrice(priceRequestDto));
    }

}
