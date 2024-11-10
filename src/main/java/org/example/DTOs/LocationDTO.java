package org.example.DTOs;

import jakarta.validation.constraints.NotNull;

@NotNull
public record LocationDTO(String name) {}
