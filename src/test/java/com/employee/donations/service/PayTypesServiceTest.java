package com.employee.donations.service;

import com.employee.donations.Utils.ListPayTypesValidator;
import com.employee.donations.mapper.PayTypeMapper;
import com.employee.donations.model.PayType;
import com.employee.donations.service.http.EmployeeStatusResponse;
import com.employee.donations.service.http.PayTypesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Payment Types Service Test")
class PayTypesServiceTest {

    @Mock
    private PayTypeMapper payTypeMapper;

    @Mock
    private ListPayTypesValidator listPayTypesValidator;

    @InjectMocks
    private DonationService sut;

    private PayType payType = new PayType(1,"debito");
    private PayType payType1 = new PayType(2,"credito");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When PayTypeStatusMapper throws Exception. Should return 500 (Internal Server Error)")
    public void obtainPayTypesList_PayTypeMapperThrowException_ReturnsInternalServerError(){
        when(payTypeMapper.obtainPayTypesList()).thenThrow(new RuntimeException("something bad happened"));
        ResponseEntity<PayTypesResponse> responseEntity = sut.obtainPayTypesList();
        assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("When PayTypesList throws Exception. Should return 204 (No Content)")
    public void obtainPayTypesList_PayTypesListIsEmpty_ReturnsNoContent(){
        ArrayList<PayType> emptyList = new ArrayList<>();
        when(payTypeMapper.obtainPayTypesList()).thenReturn(emptyList);
        when(listPayTypesValidator.apply(emptyList)).thenReturn(ResponseEntity.noContent().build());
        ResponseEntity<PayTypesResponse> responseEntity = sut.obtainPayTypesList();
        assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("When No Exception is Caught. Should return 200 (OK)")
    public void obtainPayTypesList_NoExceptionCaught_ReturnsOk(){
        List<PayType> payTypeList = Arrays.asList(payType, payType1);
        when(payTypeMapper.obtainPayTypesList()).thenReturn(payTypeList);
        when(listPayTypesValidator.apply(payTypeList))
                .thenReturn(ResponseEntity.ok(new PayTypesResponse(payTypeList)));
        ResponseEntity<PayTypesResponse> responseEntity = sut.obtainPayTypesList();
        assertThat("Status Code Response", responseEntity.getStatusCode(),is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getPayTypesList().get(0).toString(),
                is(payTypeList.get(0).toString()));
    }
}