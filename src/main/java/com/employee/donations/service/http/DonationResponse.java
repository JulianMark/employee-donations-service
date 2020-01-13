package com.employee.donations.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DonationResponse {

    private Byte result;
    private String errorMessage;

    public DonationResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
