package enzosdev.bjjtrack.service;


import enzosdev.bjjtrack.dto.request.LoginRequest;
import enzosdev.bjjtrack.dto.response.LoginResponse;
import enzosdev.bjjtrack.entity.Scope;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder  jwtEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }


    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIgnoreCaseAndAcademyId(loginRequest.getEmail(), loginRequest.getAcademyId())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        List<String> scopes = user.getScopes().stream()
                .map(Scope::getName)
                .toList();

        long expiresIn = 600L;
        JwtClaimsSet jwtClaims = JwtClaimsSet.builder()
                .issuer("BjjTrack")
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .claim("scope", scopes)
                .claim("email", user.getEmail())
                .claim("academyId", user.getAcademy().getId())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaims)).getTokenValue();

        return LoginResponse.builder()
                .token(token)
                .expiresIn(expiresIn)
                .build();
    }

}
