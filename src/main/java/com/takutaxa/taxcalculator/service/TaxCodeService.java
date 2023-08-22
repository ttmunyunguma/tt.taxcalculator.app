package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.TaxCode;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaxCodeService {

    public List<TaxCode> getAllTaxCodes();
}
