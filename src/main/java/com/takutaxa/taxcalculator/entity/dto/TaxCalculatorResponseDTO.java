package com.takutaxa.taxcalculator.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TaxCalculatorResponseDTO {
    private Double annualGrossSalary;
    private Double annualNetSalary;
    private Double annualIncomeTax;
    private Double annualNationalInsurance;

    private Double monthlyGrossSalary;
    private Double monthlyNetSalary;
    private Double monthlyIncomeTax;
    private Double monthlyNationalInsurance;

    private Double weeklyGrossSalary;
    private Double weeklyNetSalary;
    private Double weeklyIncomeTax;
    private Double weeklyNationalInsurance;
}
