package com.group.architecture.globe.model.entity;

import com.group.architecture.globe.model.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Continent extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "continent")
    private Set<Country> countries;
}
