package com.br.authentication.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "createdTime", columnDefinition = "timestamp")
    private ZonedDateTime createdTime;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "updatedTime", columnDefinition = "timestamp")
    private ZonedDateTime updatedTime;

    @Column(name = "updatedBy")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        createdTime = ZonedDateTime.now();
        updatedTime = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = ZonedDateTime.now();
    }
}
