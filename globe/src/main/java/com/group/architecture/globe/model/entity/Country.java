package com.group.architecture.globe.model.entity;

import com.group.architecture.globe.model.common.BaseEntity;
import com.group.architecture.globe.model.request.CountryRequest;
import lombok.*;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Country extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, name = "A2",length = 2)
    private String countryCode2;

    @Column(nullable = false, unique = true, name = "A3",length = 3)
    private String countryCode3;

    @ManyToOne(optional = false)
    @JoinColumn(name = "continent_id")
    private Continent continent;

    public Country(CountryRequest request) {
        this.name = request.getName();
        this.countryCode2 = request.getCode2();
        this.countryCode3 = request.getCode3();;
    }
}
