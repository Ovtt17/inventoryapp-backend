package com.inventoryapp.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String email,
        String name,
        String profilePicture
) {
}
