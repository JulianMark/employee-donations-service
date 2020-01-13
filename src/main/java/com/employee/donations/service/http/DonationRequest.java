package com.employee.donations.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequest {

    private Float amount;
    private Integer payType;
    private Integer idEmployee;
    private Integer idCampaign;
    private String dni;
    private String name;
    private String lastName;
    private Integer age;
    private String gender;
}
