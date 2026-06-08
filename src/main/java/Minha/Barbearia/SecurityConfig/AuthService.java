package Minha.Barbearia.SecurityConfig;

import Minha.Barbearia.Clientes.ClientesRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

private ClientesRepository clientesRepository;

    public AuthService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        return clientesRepository.findByNome(nome);
    }
}
