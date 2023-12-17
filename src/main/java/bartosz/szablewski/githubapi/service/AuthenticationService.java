package bartosz.szablewski.githubapi.service;

import bartosz.szablewski.githubapi.exception.EmailDuplicatedException;
import bartosz.szablewski.githubapi.model.Role;
import bartosz.szablewski.githubapi.model.User;
import bartosz.szablewski.githubapi.model.auth.AuthenticationRequest;
import bartosz.szablewski.githubapi.model.auth.AuthenticationResponse;
import bartosz.szablewski.githubapi.model.auth.RegisterRequest;
import bartosz.szablewski.githubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new EmailDuplicatedException(String.format("Given email: %s already exist", request.getEmail()));
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        log.info("Created new user: {}", user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        var user = repository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("Authenticate user: {}", user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
