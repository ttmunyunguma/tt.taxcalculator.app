package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorRequestDTO;
import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorResponseDTO;

public interface TaxCalculatorService {
    TaxCalculatorResponseDTO calculateNetSalary(TaxCalculatorRequestDTO taxCalculatorRequestDTO);
}
