package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.TaxCode;
import com.takutaxa.taxcalculator.repository.TaxCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxCodeServiceImpl implements TaxCodeService{

    private final TaxCodeRepository taxCodeRepository;

    @Override
    public List<TaxCode> getAllTaxCodes() {
        return taxCodeRepository.findAll();
    }
}
