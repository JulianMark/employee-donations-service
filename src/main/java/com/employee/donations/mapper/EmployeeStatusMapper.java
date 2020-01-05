package com.employee.donations.mapper;


import com.employee.donations.service.http.EmployeeStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface EmployeeStatusMapper {

EmployeeStatusResponse obtainStatus (@Param("idEmployee") Integer idEmployee);
}
