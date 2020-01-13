package com.employee.donations.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static com.employee.donations.Utils.Utils.validateIdNumber;
import static com.employee.donations.Utils.Utils.validateRequest;


@DisplayName("Utils Service Test")
class UtilsTest {

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When Request is null")
    void validateRequest_RequestIsNull_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateRequest(null));
    }

    @Test
    @DisplayName("When IdNumber is null or less zero")
    void validateIdNumber_IdNumberIsNullOrLessZero_ReturnsIllegalArgumentException () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateIdNumber(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateIdNumber(-1));
    }
}