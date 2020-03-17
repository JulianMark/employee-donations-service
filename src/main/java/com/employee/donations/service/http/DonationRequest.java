package com.employee.donations.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequest {

    private String name;
    private String lastName;
    private String dni;
    private String dateBirth;
    private String gender;
    private String mobile;
    private String phone;
    private String address;
    private String city;
    private String province;
    private Float amount;
    private String cardNumber;
    private String cardholder;
    private String dniCardholder;
    private String expirationDate;
    private String observation;
    private Integer payType;
    private Integer idEmployee;
    private Integer idCampaign;
}
