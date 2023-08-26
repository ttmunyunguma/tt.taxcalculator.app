package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.dto.TaxCodeDTO;

import java.util.List;

public interface TaxCodeService {

    List<TaxCodeDTO> getAllTaxCodes();
}
