package com.employee.donations.service;

import com.employee.donations.Utils.Utils;
import com.employee.donations.mapper.EmployeeStatusMapper;
import com.employee.donations.service.http.EmployeeStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@RestController
public class DonationService {

    private final Utils utils;
    private final EmployeeStatusMapper employeeStatusMapper;

    @Autowired
    public DonationService(Utils utils, EmployeeStatusMapper employeeStatusMapper) {
        this.utils = utils;
        this.employeeStatusMapper = employeeStatusMapper;
    }

    @GetMapping(
            value = "employee/status/{idEmployee}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeStatusResponse> obtainStatus(@PathVariable Integer idEmployee) {
        try{
            utils.validateIdNumber(idEmployee);
            EmployeeStatusResponse employeeStatusResponse = employeeStatusMapper.obtainStatus(idEmployee);
            if (employeeStatusResponse == null) {
                LOGGER.info("No se encuentra vinculado a ninguna campania el empleado id: "+idEmployee);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new EmployeeStatusResponse("No se encuentra vinculado a ninguna campania"));
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

   // public ResponseEntity<DonationResponse> addDonation (@RequestBody )
}
