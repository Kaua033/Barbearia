package Minha.Barbearia.SecurityConfig;

import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Clientes.ClientesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Teste unitário do AuthService.
 *
 * AuthService implementa UserDetailsService do Spring Security.
 * Ele busca o cliente pelo nome no ClientesRepository.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private ClientesRepository clientesRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Deve carregar usuário por nome com sucesso")
    void deveCarregarUsuarioPorNome() {
        ClienteModel cliente = new ClienteModel();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setSenha("senhaCriptografada");

        when(clientesRepository.findByNome("João")).thenReturn(cliente);

        UserDetails userDetails = authService.loadUserByUsername("João");

        assertNotNull(userDetails);
        assertEquals("João", userDetails.getUsername());
        assertEquals("senhaCriptografada", userDetails.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(clientesRepository.findByNome("Inexistente")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> authService.loadUserByUsername("Inexistente"));
    }
}
