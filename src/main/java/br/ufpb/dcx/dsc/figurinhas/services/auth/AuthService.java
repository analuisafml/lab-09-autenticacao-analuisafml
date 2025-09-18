package br.ufpb.dcx.dsc.figurinhas.services.auth;

import br.ufpb.dcx.dsc.figurinhas.config.security.JwtService;
import br.ufpb.dcx.dsc.figurinhas.dto.auth.AuthRequest;
import br.ufpb.dcx.dsc.figurinhas.dto.auth.AuthResponse;
import br.ufpb.dcx.dsc.figurinhas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}