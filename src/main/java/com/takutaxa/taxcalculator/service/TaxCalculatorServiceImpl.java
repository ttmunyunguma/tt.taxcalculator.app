package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorRequestDTO;
import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorServiceImpl implements TaxCalculatorService {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int WEEKS_IN_YEAR = 52;
    private static final double PERSONAL_ALLOWANCES = 12_570.00;
    private static final double BASIC_RATE_LIMIT = 37_699.00;
    private static final double HIGHER_RATE_LIMIT = 74_869.00;
    private static final double BASIC_RATE = 0.20;
    private static final double HIGHER_RATE = 0.40;
    private static final double ADDITIONAL_RATE = 0.45;
    private static final double NI_WEEKLY_LOWER_THRESHOLD = 242;
    private static final double NI_WEEKLY_UPPER_THRESHOLD = 967;
    private static final double NI_MONTHLY_LOWER_THRESHOLD = 1_048;
    private static final double NI_MONTHLY_UPPER_THRESHOLD = 4_189;
    private static final double NI_LOWER_RATE = 0.12;
    private static final double NI_UPPER_RATE = 0.02;

    @Override
    public TaxCalculatorResponseDTO calculateNetSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        if (taxCalculatorRequestDTO.getTaxCode() == null)
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
                .annualIncomeTax(annualIncomeTax)
                .annualNationalInsurance(calculateAnnualNationalInsurance(grossAnnualSalary))
                .monthlyGrossSalary(calculateMonthlyGrossSalary(taxCalculatorRequestDTO))
                .monthlyIncomeTax(annualIncomeTax / MONTHS_IN_YEAR)
                .monthlyNationalInsurance(calculateMonthlyNationalInsurance(calculateMonthlyGrossSalary(taxCalculatorRequestDTO)))
                .weeklyGrossSalary(calculateWeeklyGrossSalary(taxCalculatorRequestDTO))
                .weeklyIncomeTax(annualIncomeTax / WEEKS_IN_YEAR)
                .weeklyNationalInsurance(calculateWeeklyNationalInsurance(calculateWeeklyGrossSalary(taxCalculatorRequestDTO)))
                .build();
    }

    private Double calculateAnnualGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()) {
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * WEEKS_IN_YEAR;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * MONTHS_IN_YEAR;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary();
        };
    }

    private Double calculateMonthlyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()) {
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary() * WEEKS_IN_YEAR / MONTHS_IN_YEAR;
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary();
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / MONTHS_IN_YEAR;
        };
    }

    private Double calculateWeeklyGrossSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO) {
        return switch (taxCalculatorRequestDTO.getSalaryFrequency()) {
            case WEEKLY -> taxCalculatorRequestDTO.getGrossSalary();
            case MONTHLY -> taxCalculatorRequestDTO.getGrossSalary() * MONTHS_IN_YEAR / WEEKS_IN_YEAR;
            case ANNUALLY -> taxCalculatorRequestDTO.getGrossSalary() / WEEKS_IN_YEAR;
        };
    }

    private double calculateIncomeTax(double annualTaxableIncome) {
        if (annualTaxableIncome < 0)
            return 0;

        double basicRateTax;
        double higherRateIncome = annualTaxableIncome - BASIC_RATE_LIMIT;

        if (higherRateIncome > 0)
            basicRateTax = BASIC_RATE_LIMIT * BASIC_RATE;
        else
            return annualTaxableIncome * BASIC_RATE;

        double higherRateTax;
        double additionalRateIncome = higherRateIncome - HIGHER_RATE_LIMIT;
        if (additionalRateIncome > 0)
            higherRateTax = HIGHER_RATE_LIMIT * HIGHER_RATE;
        else
            return basicRateTax + (higherRateIncome * HIGHER_RATE);

        return basicRateTax + higherRateTax + (additionalRateIncome * ADDITIONAL_RATE);
    }

    private double calculateWeeklyNationalInsurance(Double weeklyGrossSalary) {
        return calculateNI(weeklyGrossSalary, NI_WEEKLY_LOWER_THRESHOLD, NI_WEEKLY_UPPER_THRESHOLD);
    }

    private double calculateMonthlyNationalInsurance(Double monthlyGrossSalary) {
        return calculateNI(monthlyGrossSalary, NI_MONTHLY_LOWER_THRESHOLD, NI_MONTHLY_UPPER_THRESHOLD);
    }

    private double calculateAnnualNationalInsurance(Double annualGrossSalary){
        double monthlyGrossSalary = annualGrossSalary / MONTHS_IN_YEAR;
        return calculateNI(monthlyGrossSalary, NI_MONTHLY_LOWER_THRESHOLD, NI_MONTHLY_UPPER_THRESHOLD);
    }

    private double calculateNI(Double grossSalary, double niLowerThreshold, double niUpperThreshold) {
        if (grossSalary < niLowerThreshold)
            return 0;

        if(grossSalary < niUpperThreshold)
            return (grossSalary - niLowerThreshold) * NI_LOWER_RATE;
        else {
            double lowerNI = NI_LOWER_RATE * (niUpperThreshold - niLowerThreshold);
            double upperNI = NI_UPPER_RATE * (grossSalary - niUpperThreshold);
            return lowerNI + upperNI;
        }
    }
}
