package com.group.architecture.globe.model.entity;

import com.group.architecture.globe.model.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Country extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, name = "code")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;
}
