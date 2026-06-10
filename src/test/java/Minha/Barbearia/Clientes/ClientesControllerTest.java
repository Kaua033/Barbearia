package Minha.Barbearia.Clientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste do ClientesController usando MockMvc standalone.
 *
 * Diferente do @WebMvcTest, usamos MockMvcBuilders.standaloneSetup()
 * que é mais leve e não precisa carregar o Spring Boot.
 * O service é mockado com @Mock do Mockito (não @MockBean).
 */
@ExtendWith(MockitoExtension.class)
class ClientesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientesSevice clientesSevice;

    @InjectMocks
    private ClientesController clientesController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        /*
         * Configura o MockMvc manualmente com o controller real
         * e seus mocks injetados. Muito mais rápido que @WebMvcTest.
         */
        mockMvc = MockMvcBuilders.standaloneSetup(clientesController).build();
    }

    @Test
    @DisplayName("GET /v1/cliente/listar/{id} deve retornar 200 com o cliente")
    void deveListarCliente() throws Exception {
        ClienteDTO dto = new ClienteDTO(1L, "João", "11999999999", null);
        when(clientesSevice.LISTARid(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/cliente/listar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[0].telefone").value("11999999999"));
    }

    @Test
    @DisplayName("POST /v1/cliente/Criar deve retornar 200 com o cliente criado")
    void deveCriarCliente() throws Exception {
        ClienteDTO dtoRequest = new ClienteDTO(null, "Maria", "11988888888", "senha456");
        ClienteDTO dtoResponse = new ClienteDTO(2L, "Maria", "11988888888", null);
        when(clientesSevice.CRIAR(any(ClienteDTO.class))).thenReturn(dtoResponse);

        mockMvc.perform(post("/v1/cliente/Criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("DELETE /v1/cliente/deletar/{id} deve retornar 200")
    void deveDeletarCliente() throws Exception {
        mockMvc.perform(delete("/v1/cliente/deletar/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /v1/cliente/atualizar/{id} deve retornar 200 com cliente atualizado")
    void deveAtualizarCliente() throws Exception {
        ClienteDTO dtoAtualizado = new ClienteDTO(1L, "João Atualizado", "11977777777", null);
        when(clientesSevice.AUTALIZAR(any(ClienteDTO.class), eq(1L))).thenReturn(dtoAtualizado);

        mockMvc.perform(put("/v1/cliente/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }
}
