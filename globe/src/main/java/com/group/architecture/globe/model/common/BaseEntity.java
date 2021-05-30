package com.group.architecture.globe.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    public static final String CREATED_DATE_FIELD_NAME = "created";
    public static final String MODIFIED_DATE_FIELD_NAME = "modified";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public static final long serialVersionUID = 50L;


    @Version
    @JsonIgnore
    protected Long version;

    @Column(updatable =false)
    @CreationTimestamp
    @JsonIgnore
    protected Date created;

    @UpdateTimestamp
    @JsonIgnore
    protected Date modified;
}
