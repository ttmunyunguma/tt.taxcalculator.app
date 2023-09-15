package com.takutaxa.taxcalculator.controller;

import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorRequestDTO;
import com.takutaxa.taxcalculator.entity.dto.TaxCalculatorResponseDTO;
import com.takutaxa.taxcalculator.service.TaxCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaxCalculatorController {

    private final TaxCalculatorService taxCalculatorService;

    @PostMapping("/calculate-net-income")
    public ResponseEntity<TaxCalculatorResponseDTO> calculateNetIncome(@RequestBody TaxCalculatorRequestDTO request){
        TaxCalculatorResponseDTO response = taxCalculatorService.calculateNetSalary(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
