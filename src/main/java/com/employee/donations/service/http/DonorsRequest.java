package com.employee.donations.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DonorsRequest {

    private String dni;
    private String name;
    private String lastName;
    private Integer age;
}
