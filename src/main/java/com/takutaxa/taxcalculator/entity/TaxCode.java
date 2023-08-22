package com.takutaxa.taxcalculator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tax_codes", schema = "public")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class TaxCode {
    @Id
    public int id;
    @Column(nullable = false)
    public String code;
    @Column
    public String summary;
}
