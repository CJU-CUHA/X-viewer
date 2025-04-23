package org.example.xviewer.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class TimeBaseEntity {

    private LocalDateTime created;
}
