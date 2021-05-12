package ru.naumen.ectmapi.entity;

import lombok.Data;

@Data
public abstract class Entity<ID> {

    private ID id;

    public boolean isNew() {
        return id == null;
    }
}
