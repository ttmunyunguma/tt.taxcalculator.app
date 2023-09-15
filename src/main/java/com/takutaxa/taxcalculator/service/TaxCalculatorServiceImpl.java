package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorRequestDTO;
import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorServiceImpl implements TaxCalculatorService{

    private static final int MONTHS_IN_YEAR = 12;
    private static final int WEEKS_IN_YEAR = 52;
    private static final double PERSONAL_ALLOWANCES = 12_570.00;
    private static final double BASIC_RATE_LIMIT = 37_699.00;
    private static final double HIGHER_RATE_LIMIT = 74_869.00;
    private static final double BASIC_RATE = 0.20;
    private static final double HIGHER_RATE = 0.40;
    private static final double ADDITIONAL_RATE = 0.45;

    @Override
    public TaxCalculatorResponseDTO calculateNetSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        if(taxCalculatorRequestDTO.getTaxCode() == null)
            return calculateNetSalaryDefaultTaxCode(taxCalculatorRequestDTO);
        return null;
    }

    //only for tax code 1257L
    private TaxCalculatorResponseDTO calculateNetSalaryDefaultTaxCode(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        double grossAnnualSalary = calculateAnnualGrossSalary(taxCalculatorRequestDTO);
        double annualTaxableIncome = grossAnnualSalary - PERSONAL_ALLOWANCES;
        double annualIncomeTax = calculateIncomeTax(annualTaxableIncome);

        return TaxCalculatorResponseDTO.builder()
                .annualGrossSalary(calculateAnnualGrossSalary(taxCalculatorRequestDTO))
                .monthlyGrossSalary(calculateMonthlyGrossSalary(taxCalculatorRequestDTO))
                .weeklyGrossSalary(calculateWeeklyGrossSalary(taxCalculatorRequestDTO))
                .build();
    }

    private Double calculateAnnualGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * WEEKS_IN_YEAR;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * MONTHS_IN_YEAR;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary();
        };
    }

    private Double calculateMonthlyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * WEEKS_IN_YEAR / MONTHS_IN_YEAR;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary();
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / MONTHS_IN_YEAR;
        };
    }

    private Double calculateWeeklyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()){
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary();
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * MONTHS_IN_YEAR / WEEKS_IN_YEAR;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / WEEKS_IN_YEAR;
        };
    }

    private double calculateIncomeTax(double annualTaxableIncome) {
        double basicRateTax;
        double higherRateTax;

        if (annualTaxableIncome < 0)
            return 0;

        double higherRateIncome = annualTaxableIncome - BASIC_RATE_LIMIT;
        if (higherRateIncome > 0)
            basicRateTax = BASIC_RATE_LIMIT * BASIC_RATE;
        else
            return annualTaxableIncome * BASIC_RATE;

        double additionalRateIncome = higherRateIncome - HIGHER_RATE_LIMIT;
        if (additionalRateIncome > 0)
            higherRateTax = HIGHER_RATE_LIMIT * HIGHER_RATE;
        else
            return basicRateTax + (higherRateIncome * HIGHER_RATE);

        return basicRateTax + higherRateTax + (additionalRateIncome * ADDITIONAL_RATE);
    }


}
