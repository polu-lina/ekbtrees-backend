package ru.naumen.ectmauth.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(allowableValues = {"fb", "vk"}, type = "String")
public enum Provider {
    local,
    fb,
    vk
}
