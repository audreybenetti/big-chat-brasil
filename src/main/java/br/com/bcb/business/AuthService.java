package br.com.bcb.business;

import br.com.bcb.api.auth.LoginRequest;
import br.com.bcb.api.auth.LoginResponse;
import br.com.bcb.api.auth.RegisterRequest;
import br.com.bcb.config.exceptions.NoRecordsFoundException;
import br.com.bcb.config.exceptions.RecordConflictException;
import br.com.bcb.config.exceptions.UserNotAuthorizedException;
import br.com.bcb.config.security.TokenService;
import br.com.bcb.repository.Usuario;
import br.com.bcb.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public LoginResponse validarLogin(LoginRequest loginRequest) {
        Usuario usuario = buscarUsuarioPorEmail(loginRequest.email());
        if (passwordEncoder.matches(loginRequest.password(), usuario.getPassword())) {
            String token = tokenService.gerarToken(usuario);
            return new LoginResponse(usuario.getName(), usuario.getEmail(), token, usuario.getRole());
        } else {
            throw new UserNotAuthorizedException("Usuário não autorizado.");
        }
    }

    public LoginResponse registrarUsuario(RegisterRequest registerRequest) {
        if(usuarioExiste(registerRequest.email())) {
            throw new RecordConflictException("Usuário já existe.");
        } else {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setPassword(passwordEncoder.encode(registerRequest.password()));
            novoUsuario.setEmail(registerRequest.email());
            novoUsuario.setName(registerRequest.name());
            novoUsuario.setRole(registerRequest.role());
            usuarioRepository.save(novoUsuario);

            String token = tokenService.gerarToken(novoUsuario);
            return new LoginResponse(novoUsuario.getName(), novoUsuario.getEmail(), token, novoUsuario.getRole());
        }
    }

    public boolean usuarioExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }


    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NoRecordsFoundException("Usuário não encontrado."));
    }
}
