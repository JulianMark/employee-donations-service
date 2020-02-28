package com.employee.donations.service.http;

import com.employee.donations.model.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class  PayTypesResponse {

    @ApiModelProperty(notes="Lista tipos de pago")
    private List<PayType> payTypesList;
    @ApiModelProperty(notes="Mensaje de error, en caso de que falle el WS")
    private String errorMessage;

    public PayTypesResponse(List<PayType> payTypesList) {
        this.payTypesList = payTypesList;
    }

    public PayTypesResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
