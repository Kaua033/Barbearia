package Minha.Barbearia.SecurityConfig;

import Minha.Barbearia.Clientes.ClienteModel;
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


    /*
     * Carrega o usuário pelo nome.
     * Se não encontrar, lança UsernameNotFoundException (exigido pelo Spring Security).
     */
    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        ClienteModel cliente = clientesRepository.findByNome(nome);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuário " + nome + " não encontrado");
        }
        return cliente;
    }
}
