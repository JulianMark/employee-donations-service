package com.employee.donations.service.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusResponse {

    private Integer idCampaign;
    private String nameCampaign;
    private Integer idOSC;
    private String descriptionOSC;
    private String errorMessage;

    public EmployeeStatusResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
