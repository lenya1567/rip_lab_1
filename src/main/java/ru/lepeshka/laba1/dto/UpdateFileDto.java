package ru.lepeshka.laba1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateFileDto {
    @NotNull
    @Size(min = 1, max = 64)
    private String path;
    private String value;

    UpdateFileDto() {
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }
}
