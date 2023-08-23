package com.takutaxa.taxcalculator.controller;

import com.takutaxa.taxcalculator.entity.TaxCodeDTO;
import com.takutaxa.taxcalculator.service.TaxCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaxCodeController {

    private final TaxCodeService taxCodeService;

    @GetMapping("/tax-codes")
    public ResponseEntity<List<TaxCodeDTO>> getAllTaxCodes(){
        List<TaxCodeDTO> taxCodeDTOS = taxCodeService.getAllTaxCodes();
        return ResponseEntity.ok(taxCodeDTOS);
    }
}
