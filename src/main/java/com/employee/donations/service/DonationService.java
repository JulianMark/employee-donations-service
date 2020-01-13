package com.employee.donations.service;

import com.employee.donations.mapper.DonationMapper;
import com.employee.donations.mapper.EmployeeStatusMapper;
import com.employee.donations.service.http.DonationRequest;
import com.employee.donations.service.http.DonationResponse;
import com.employee.donations.service.http.EmployeeStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.employee.donations.Utils.Utils.validateIdNumber;
import static com.employee.donations.Utils.Utils.validateRequest;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@RestController
public class DonationService {

    private final EmployeeStatusMapper employeeStatusMapper;
    private final DonationMapper donationMapper;

    @Autowired
    public DonationService(EmployeeStatusMapper employeeStatusMapper,
                           DonationMapper donationMapper) {
        this.employeeStatusMapper = employeeStatusMapper;
        this.donationMapper = donationMapper;
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
            LOGGER.info("Se obtuvo la campania para el empleado "+idEmployee);
            return ResponseEntity.ok(employeeStatusResponse);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos: ",iae);
            return ResponseEntity.badRequest().body(new EmployeeStatusResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener las OSC para el empleado con id: "+idEmployee);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmployeeStatusResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/donation/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DonationResponse> addDonation (@RequestBody DonationRequest donationRequest){
        try{
            validateRequest(donationRequest);
            donationMapper.addDonation(donationRequest);
            LOGGER.info("Se ingreso correctamente la donacion");
            return ResponseEntity.ok(new DonationResponse((byte) 0, null));
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos: ",iae);
            return ResponseEntity.badRequest().body(new DonationResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar ingresar la donacion");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DonationResponse(ex.getMessage()));
        }
    }
}
