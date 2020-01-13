package com.employee.donations.service;

import com.employee.donations.mapper.DonationMapper;
import com.employee.donations.service.http.DonationRequest;
import com.employee.donations.service.http.DonationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@DisplayName("Insert Donation Service Test")
class InsertDonationServiceTest {

    @Mock
    private DonationMapper donationMapper;

    @InjectMocks
    private DonationService sut;

    private DonationRequest VALID_REQUEST = new DonationRequest(300f
                                                                ,1
                                                                ,1
                                                                ,1
                                                                ,"30987765"
                                                                ,"Milca"
                                                                ,"Riño"
                                                                ,18
                                                                ,"f");

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When Request is null (400 BAD REQUEST)")
    public void addDonation_RequestIsNull_ReturnsBadRequest() {
        ResponseEntity<DonationResponse> responseEntity = sut.addDonation(null);
        assertThat("Status Code Response"
        ,responseEntity.getStatusCode()
        ,is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("When DonationMapper throws Exception should return 500 (Internal Server Error)")
    void addDonation_DonationMapperThrowException_ReturnsInternalServerError(){
        doThrow(new RuntimeException()).when(donationMapper).addDonation(any());
        ResponseEntity<DonationResponse> responseEntity = sut.addDonation(VALID_REQUEST);
        assertThat("Status Code Response",
                responseEntity.getStatusCode(),
                is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("When no Exception is caught should return 200 (OK)")
    void addDonation_NoExceptionCaught_ReturnsOK(){
        doNothing().when(donationMapper).addDonation(any());
        ResponseEntity<DonationResponse> responseEntity = sut.addDonation(VALID_REQUEST);
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
    }

}