package Minha.Barbearia.SecurityConfig;

import Minha.Barbearia.Clientes.ClienteDTO;
import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Clientes.ClientesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste do AuthController com MockMvc standalone.
 *
 * Testa os endpoints de registro (register) e login
 * sem carregar o Spring Boot, usando Mockito puro.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("POST /auth/register deve registrar usuário com sucesso")
    void deveRegistrarUsuario() throws Exception {
        ClienteDTO dto = new ClienteDTO(null, "João", "11999999999", "senha123");
        when(passwordEncoder.encode("senha123")).thenReturn("$2a$10$encoded");
        when(clientesRepository.save(any(ClienteModel.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("usuario criado"));
    }

    @Test
    @DisplayName("POST /auth/login deve autenticar com sucesso")
    void deveAutenticarLogin() throws Exception {
        ClienteDTO dto = new ClienteDTO(null, "João", null, "senha123");

        ClienteModel cliente = new ClienteModel();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setTelefone("11999999999");
        cliente.setSenha("$2a$10$encoded");

        when(clientesRepository.findByNome("João")).thenReturn(cliente);
        when(passwordEncoder.matches("senha123", "$2a$10$encoded")).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /auth/login deve retornar 401 quando senha inválida")
    void deveRetornar401QuandoSenhaInvalida() throws Exception {
        ClienteDTO dto = new ClienteDTO(null, "João", null, "senhaErrada");

        ClienteModel cliente = new ClienteModel();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setSenha("$2a$10$encoded");

        when(clientesRepository.findByNome("João")).thenReturn(cliente);
        when(passwordEncoder.matches("senhaErrada", "$2a$10$encoded")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("usuario ou senha invalidos"));
    }

    @Test
    @DisplayName("POST /auth/login deve retornar 401 quando usuário não existe")
    void deveRetornar401QuandoUsuarioNaoExiste() throws Exception {
        ClienteDTO dto = new ClienteDTO(null, "Inexistente", null, "senha123");
        when(clientesRepository.findByNome("Inexistente")).thenReturn(null);
        when(clientesRepository.findByTelefone("Inexistente")).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("usuario ou senha invalidos"));
    }
}
