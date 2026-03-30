package com.app.inventory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
@Getter
public abstract class AuditableEntity extends BaseEntity {
  @CreatedDate
  @Column(name = "\"CREATED_AT\"", nullable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "\"UPDATED_AT\"", nullable = false)
  private Instant updatedAt;

  @CreatedBy
  @Column(name = "\"CREATED_BY\"", nullable = false)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "\"UPDATED_BY\"", nullable = false)
  private String updatedBy;
}
