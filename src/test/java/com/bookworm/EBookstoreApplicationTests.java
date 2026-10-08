package com.bookworm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EBookstoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCategories() throws Exception {
        mockMvc.perform(get("/api/v1/catalog/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetBooksCatalog() throws Exception {
        mockMvc.perform(get("/api/v1/catalog/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUserLogin() throws Exception {
        String loginJson = "{\"email\":\"daniel@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.fullName").value("Daniel Reed"));
    }

    @Test
    void testCouponValidation() throws Exception {
        String couponJson = "{\"couponCode\":\"SAVE100\",\"cartTotal\":508.00}";
        mockMvc.perform(post("/api/v1/payments/validate-coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(couponJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid").value(true))
                .andExpect(jsonPath("$.discountAmount").value(100.00));
    }

    @Test
    void testShippingCalculation() throws Exception {
        String shippingJson = "{\"pin\":\"400001\",\"cartTotal\":508.00}";
        mockMvc.perform(post("/api/v1/shipping/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shippingJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFreeDelivery").value(true));
    }
}
