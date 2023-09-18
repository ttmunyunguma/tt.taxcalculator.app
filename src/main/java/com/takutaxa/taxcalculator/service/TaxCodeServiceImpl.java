package com.takutaxa.taxcalculator.service;

import com.takutaxa.taxcalculator.entity.TaxCode;
import com.takutaxa.taxcalculator.entity.dto.TaxCodeDTO;
import com.takutaxa.taxcalculator.repository.TaxCodeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxCodeServiceImpl implements TaxCodeService{

    private final TaxCodeRepository taxCodeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TaxCodeDTO> getAllTaxCodes() {
        List<TaxCode> taxCodes = taxCodeRepository.findAll();
        return taxCodes.stream().map(this::convertTaxCodeToTaxCodeDTO).toList();
    }

    private TaxCodeDTO convertTaxCodeToTaxCodeDTO(TaxCode taxCode){
        return modelMapper.map(taxCode, TaxCodeDTO.class);
    }

    private TaxCode convertTaxCodeDTOToTaxCode(TaxCodeDTO taxCodeDTO){
        return modelMapper.map(taxCodeDTO, TaxCode.class);
    }
}
