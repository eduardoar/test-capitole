package com.rosales.springtest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {
    private final MockMvc mockMvc;
    private static final Long VALID_ID_PRODUCT = 35455L;
    private static final Integer VALID_ID_BRAND = 1;
    private static final String PATH = "/api/v1/prices";

    @Autowired
    public PriceControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testValidPriceRequest() throws Exception {
        testValidPriceRequest("2020-06-14T10:00:00", 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 35.50);
        testValidPriceRequest("2020-06-14T16:00:00", 2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 25.45);
        testValidPriceRequest("2020-06-14T21:00:00", 1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 35.50);
        testValidPriceRequest("2020-06-15T10:00:00", 3, "2020-06-15T00:00:00", "2020-06-15T11:00:00", 30.50);
        testValidPriceRequest("2020-06-16T21:00:00", 4, "2020-06-15T16:00:00", "2020-12-31T23:59:59", 38.95);
    }

    @Test
    public void testInvalidPriceRequest() throws Exception {
        testInvalidPriceRequest(null, "35450L", "1");
        testInvalidPriceRequest("2020-06-14T10:00:00", "0L", "1");
        testInvalidPriceRequest("2020-06-14T10:00:00", "35455L", "0");
    }

    private void testValidPriceRequest(String applicationDate, int priceRate, String startDate, String endDate, double price) throws Exception {

        MultiValueMap<String, String> params = createParams(applicationDate, String.valueOf(VALID_ID_PRODUCT), String.valueOf(VALID_ID_BRAND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PATH)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProduct").value(VALID_ID_PRODUCT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idBrand").value(VALID_ID_BRAND))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPriceRate").value(priceRate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(startDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(endDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(price));
    }

    private void testInvalidPriceRequest(String applicationDate, String idProduct, String idBrand) throws Exception {

        MultiValueMap<String, String> params = createParams(applicationDate, idProduct, idBrand);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PATH)
                        .params(params))
                .andExpect(status().isBadRequest());

    }

    private MultiValueMap<String, String> createParams(String applicationDate, String idProduct, String idBrand) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("applicationDate", applicationDate);
        params.add("idProduct", idProduct);
        params.add("idBrand", idBrand);
        return params;

    }

}
