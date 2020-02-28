package com.employee.donations.utils;

import com.employee.donations.Utils.ListPayTypesValidator;
import com.employee.donations.model.PayType;
import com.employee.donations.service.http.PayTypesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Validator list of payment types")
public class ListPayTypesValidatorTest {

    @Mock
    private PayType payType = new PayType(1,"debito");

    @InjectMocks
    private ListPayTypesValidator sut;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When list is empty. Should return 204 (No Content)")
    void obtainEmptyList_ListIsEmpty_ReturnsNonContent () {
        List<PayType> emptyList = Arrays.asList();
        ResponseEntity<PayTypesResponse> responseEntity = sut.apply(emptyList);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("When list is not empty. Should return 200 (OK)")
    void obtainList_ListIsNotEmpty_ReturnsOk () {
        List<PayType> payTypeList = Arrays.asList(payType);
        ResponseEntity<PayTypesResponse> responseEntity = sut.apply(payTypeList);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }
}
