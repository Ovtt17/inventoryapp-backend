package com.inventoryapp.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String name,
        String profilePicture
) {
}
