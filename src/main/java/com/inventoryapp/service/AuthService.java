package com.inventoryapp.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.inventoryapp.dto.LoginRequest;
import com.inventoryapp.dto.RegisterRequest;
import com.inventoryapp.dto.UserResponse;
import com.inventoryapp.entity.AuthProvider;
import com.inventoryapp.entity.User;
import com.inventoryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleTokenService googleTokenService;
    private final UserRepository userRepository;

    public UserResponse authenticateWithGoogle(String idToken) {
        Payload payload = googleTokenService.verifyToken(idToken);

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");
        String subject = payload.getSubject();

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .username(subject)
                    .name(name)
                    .profilePicture(picture)
                    .provider(AuthProvider.GOOGLE)
                    .enabled(true)
                    .accountLocked(false)
                    .build();
            return userRepository.save(newUser);
        });

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profilePicture(user.getProfilePicture())
                .build();
    }
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        String username = request.getEmail() != null ? request.getEmail().split("@")[0] : null;

        User user = User.builder()
                .email(request.getEmail())
                .username(username)
                .name(request.getName())
                .password(request.getPassword())
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .accountLocked(false)
                .build();

        User userSaved = userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(userSaved.getEmail())
                .name(userSaved.getName())
                .profilePicture(userSaved.getProfilePicture())
                .build();
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email o contraseña inválidos"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Email o contraseña inválidos");
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .profilePicture(user.getProfilePicture())
                .build();
    }
}
