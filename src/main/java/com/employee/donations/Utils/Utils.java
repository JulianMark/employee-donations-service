package com.employee.donations.Utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class Utils {

    private final static String DATE_FORMAT = "yyyy/mm/dd";

    public static void validateRequest (Object request){
        if (request == null){
            throw new IllegalArgumentException("el body del metodo no debe ser nulo");
        }
    }

    public static void validateIdNumber (Integer numberId){
        if (numberId == null){
            throw new IllegalArgumentException("el id no debe ser nulo");
        }
        if (numberId <= 0){
            throw new IllegalArgumentException("el id no debe ser igual o menor a 0");
        }
    }

}
