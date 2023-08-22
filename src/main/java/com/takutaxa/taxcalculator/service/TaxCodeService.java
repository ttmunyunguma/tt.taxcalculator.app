package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.TaxCode;
import com.takutaxa.taxcalculator.entity.TaxCodeDTO;

import java.util.List;

public interface TaxCodeService {

    List<TaxCodeDTO> getAllTaxCodes();
}
