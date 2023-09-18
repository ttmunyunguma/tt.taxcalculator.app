package com.takutaxa.taxcalculator.entity.dto;

import com.takutaxa.taxcalculator.util.SalaryFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaxCalculatorRequestDTO {
    private Double grossSalary;
    private SalaryFrequency salaryFrequency;
    private String taxCode;
}
