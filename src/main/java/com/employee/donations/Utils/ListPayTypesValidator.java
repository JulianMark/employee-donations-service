package com.employee.donations.Utils;

import com.employee.donations.model.PayType;
import com.employee.donations.service.http.PayTypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ListPayTypesValidator implements Function<List<PayType>, ResponseEntity<PayTypesResponse>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListPayTypesValidator.class);

    @Override
    public ResponseEntity<PayTypesResponse> apply(List<PayType> listPayTypes) {
        if(!listPayTypes.isEmpty()) {
            LOGGER.info("the list for payment types was obtained");
            return ResponseEntity.ok().body(new PayTypesResponse(listPayTypes));
        }
        LOGGER.info("the list of pay types was not obtained");
        return ResponseEntity.noContent().build();
    }
}
