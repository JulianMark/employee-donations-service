package com.employee.donations.service;

import com.employee.donations.mapper.DonationMapper;
import com.employee.donations.mapper.EmployeeStatusMapper;
import com.employee.donations.mapper.PayTypeMapper;
import com.employee.donations.model.PayType;
import com.employee.donations.service.http.DonationRequest;
import com.employee.donations.service.http.DonationResponse;
import com.employee.donations.service.http.EmployeeStatusResponse;
import com.employee.donations.service.http.PayTypesResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.employee.donations.Utils.Utils.validateIdNumber;
import static com.employee.donations.Utils.Utils.validateRequest;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@RestController
public class DonationService {

    private final EmployeeStatusMapper employeeStatusMapper;
    private final DonationMapper donationMapper;
    private final PayTypeMapper payTypeMapper;
    private final Function<List<PayType>, ResponseEntity<PayTypesResponse>> listPayTypesValidator;

    @Autowired
    public DonationService(EmployeeStatusMapper employeeStatusMapper,
                           DonationMapper donationMapper, PayTypeMapper payTypeMapper, Function<List<PayType>, ResponseEntity<PayTypesResponse>> listPayTypesValidator) {
        this.employeeStatusMapper = employeeStatusMapper;
        this.donationMapper = donationMapper;
        this.payTypeMapper = payTypeMapper;
        this.listPayTypesValidator = listPayTypesValidator;
    }

    @GetMapping(
            value = "employee/status/{idEmployee}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeStatusResponse> obtainStatus(@PathVariable Integer idEmployee) {
        try{
            validateIdNumber(idEmployee);
            EmployeeStatusResponse employeeStatusResponse = employeeStatusMapper.obtainStatus(idEmployee);
            if (employeeStatusResponse == null) {
                LOGGER.info("No se encuentra vinculado a ninguna campania el empleado id: "+idEmployee);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new EmployeeStatusResponse("No se encuentra vinculado a ninguna campania"));
            }
            LOGGER.info("The campaign for the employee{} was obtained"+idEmployee);
            return ResponseEntity.ok(employeeStatusResponse);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("The parameters entered are invalid: ",iae);
            return ResponseEntity.badRequest().body(new EmployeeStatusResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("An error occurred while trying to get OSC for employee id {}"+idEmployee);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmployeeStatusResponse(ex.getMessage()));
        }
    }

    @CrossOrigin(origins ="http://localhost:3000", maxAge = 3600 )
    @PostMapping(
            value = "employee/donation/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationResponse> addDonation (@RequestBody DonationRequest donationRequest){
        try{
            validateRequest(donationRequest);
            donationMapper.addDonation(donationRequest);
            LOGGER.info("The donation was entered correctly");
            return ResponseEntity.ok(new DonationResponse((byte) 0, null));
        }catch (IllegalArgumentException iae){
            LOGGER.warn("The parameters entered are invalid:  ",iae);
            return ResponseEntity.badRequest().body(new DonationResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("An error occurred while trying to add donation");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DonationResponse(ex.getMessage()));
        }
    }

    @CrossOrigin(origins ="http://localhost:3000", maxAge = 3600 )
    @GetMapping(
            value = "donations/paymentTypes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayTypesResponse> obtainPayTypesList () {
        try {
            return Optional.of(payTypeMapper.obtainPayTypesList())
                    .map(listPayTypesValidator)
                    .orElseThrow(()-> new RuntimeException("Something bad happened"));
        }catch (Exception ex) {
            LOGGER.error("An error occurred while trying to get pay types");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PayTypesResponse(ex.getMessage()));
        }
    }
}
