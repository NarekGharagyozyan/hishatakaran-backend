package org.hishatakaran.backend.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;

	@PrePersist
	protected void onRegister() {
		createdAt = ZonedDateTime.now();
		updatedAt = ZonedDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		ZonedDateTime.now();
	}

}