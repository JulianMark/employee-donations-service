package com.employee.donations.service;


import com.employee.donations.mapper.EmployeeStatusMapper;
import com.employee.donations.service.http.EmployeeStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Employee Status Service Test")
class EmployeeStatusServiceTest {

    @Mock
    private EmployeeStatusMapper employeeStatusMapper;

    @InjectMocks
    private DonationService sut;

    private EmployeeStatusResponse VALID_RESPONSE = new EmployeeStatusResponse(1,"via publica",1,"Afulic", null);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainEmployeeStatusRequestTest {

        @Test
        @DisplayName("When idEmployee is null")
        public void obtainStatus_idEmployeeIsNull_ReturnBadRequest(){
            ResponseEntity<EmployeeStatusResponse> responseEntity = sut.obtainStatus(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainStatus_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<EmployeeStatusResponse> responseEntity = sut.obtainStatus(0);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }
    }
    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
    class ObtainEmployeeStatusServerErrorTest {

        @Test
        @DisplayName("When EmployeeStatusMapper throws Exception")
        public void obtainStatus_EmployeeStatusMapperThrowException_ReturnsInternalServerError(){
            when(employeeStatusMapper.obtainStatus(any())).thenThrow(new RuntimeException ("something bad happened"));
            ResponseEntity<EmployeeStatusResponse> responseEntity = sut.obtainStatus(1);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Nested
    @DisplayName("Should return 204(NO_CONTENT)")
    class ObtainEmployeeStatusResponseTest {

        @Test
        @DisplayName("When EmployeeStatusResponse is null")
        public void obtainEmployeeStatus_ResponseIsNull_ReturnNoContent() {
            when(employeeStatusMapper.obtainStatus(any())).thenReturn(null);
            ResponseEntity<EmployeeStatusResponse> responseEntity = sut.obtainStatus(1);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }

    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainEmployeeStatusOKTest {

        @Test
        @DisplayName("When No Exception is Caught")
        public void obtainStatus_NoExceptionCaught_ReturnsOk(){
            when(employeeStatusMapper.obtainStatus(any())).thenReturn(VALID_RESPONSE);
            ResponseEntity<EmployeeStatusResponse> responseEntity = sut.obtainStatus(1);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }
}