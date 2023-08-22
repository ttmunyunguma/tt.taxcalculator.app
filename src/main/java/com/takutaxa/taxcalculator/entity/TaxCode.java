package com.takutaxa.taxcalculator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
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
