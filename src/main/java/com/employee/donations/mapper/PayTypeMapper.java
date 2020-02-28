package com.employee.donations.mapper;

import com.employee.donations.model.PayType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PayTypeMapper {

    List<PayType> obtainPayTypesList();
}
