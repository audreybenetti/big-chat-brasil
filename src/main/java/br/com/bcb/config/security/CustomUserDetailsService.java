package br.com.bcb.config.security;

import br.com.bcb.config.exceptions.NoRecordsFoundException;
import br.com.bcb.repository.Usuario;
import br.com.bcb.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(() -> new NoRecordsFoundException("Usuário não encontrado."));
        return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }
}
