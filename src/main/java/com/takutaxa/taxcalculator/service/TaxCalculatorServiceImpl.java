package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorRequestDTO;
import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorServiceImpl implements TaxCalculatorService{
    @Override
    public TaxCalculatorResponseDTO calculateNetSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        if(taxCalculatorRequestDTO.getTaxCode() == null)
            return calculateNetSalaryDefaultTaxCode(taxCalculatorRequestDTO);
        return null;
    }

    private TaxCalculatorResponseDTO calculateNetSalaryDefaultTaxCode(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return TaxCalculatorResponseDTO.builder()
                .annualGrossSalary(calculateAnnualGrossSalary(taxCalculatorRequestDTO))
                .monthlyGrossSalary(calculateMonthlyGrossSalary(taxCalculatorRequestDTO))
                .weeklyGrossSalary(calculateWeeklyGrossSalary(taxCalculatorRequestDTO))
                .build();
    }

    private Double calculateWeeklyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary();
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * 12 / 52;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / 52;
        };
    }

    private Double calculateMonthlyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * 52 / 12;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary();
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / 12;
        };
    }

    private Double calculateAnnualGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * 52;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * 12;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary();
        };
    }
}
