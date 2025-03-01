package org.example.DTOs;

import jakarta.validation.constraints.NotNull;

@NotNull
public record AccountDTO(String username, String email, String password) {}
