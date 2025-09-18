package br.ufpb.dcx.dsc.figurinhas.controller.auth;

import br.ufpb.dcx.dsc.figurinhas.dto.auth.AuthRequest;
import br.ufpb.dcx.dsc.figurinhas.dto.auth.AuthResponse;
import br.ufpb.dcx.dsc.figurinhas.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Object request) {
        return ResponseEntity.ok("Pedido de mudança de senha recebido.");
    }
}