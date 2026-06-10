package Minha.Barbearia.Barbeiro;

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
 * Teste do BarbeiroController com MockMvc standalone.
 */
@ExtendWith(MockitoExtension.class)
class BarbeiroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BarbeiroService barbeiroService;

    @InjectMocks
    private BarbeiroController barbeiroController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(barbeiroController).build();
    }

    @Test
    @DisplayName("POST /v1/barbeiro/criar deve retornar 200")
    void deveCriarBarbeiro() throws Exception {
        BarbeiroDTO dto = new BarbeiroDTO("Carlos", "11911111111");
        when(barbeiroService.Criar(any(BarbeiroDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/v1/barbeiro/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /v1/barbeiro/listar/{id} deve retornar 200 com lista")
    void deveListarBarbeiro() throws Exception {
        BarbeiroDTO dto = new BarbeiroDTO("Carlos", "11911111111");
        when(barbeiroService.mostrar(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/barbeiro/listar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Carlos"));
    }

    @Test
    @DisplayName("DELETE /v1/barbeiro/delecao/{id} deve retornar 200")
    void deveDeletarBarbeiro() throws Exception {
        mockMvc.perform(delete("/v1/barbeiro/delecao/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /v1/barbeiro/atualizar/{id} deve retornar 200")
    void deveAtualizarBarbeiro() throws Exception {
        BarbeiroDTO dto = new BarbeiroDTO("Carlos Atualizado", "11922222222");
        when(barbeiroService.AUTALIZAR(any(BarbeiroDTO.class), eq(1L))).thenReturn(dto);

        mockMvc.perform(put("/v1/barbeiro/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Atualizado"));
    }
}
