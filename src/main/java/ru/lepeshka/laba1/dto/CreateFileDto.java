package ru.lepeshka.laba1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateFileDto {
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
    private String path;
    private String value;

    CreateFileDto() {
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }
}
