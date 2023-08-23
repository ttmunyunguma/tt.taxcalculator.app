package com.takutaxa.taxcalculator.repository;

import com.takutaxa.taxcalculator.entity.TaxCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxCodeRepository extends JpaRepository<TaxCode, Integer> {
}
