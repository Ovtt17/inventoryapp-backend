package com.inventoryapp.repository;

import com.inventoryapp.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@Email(message = "El email no es válido") @NotBlank(message = "El email es obligatorio") String email);
}