package Minha.Barbearia.Servico;

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
 * Teste do ServicoController com MockMvc standalone.
 */
@ExtendWith(MockitoExtension.class)
class ServicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private servicoService servicoService;

    @InjectMocks
    private ServicoController servicoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(servicoController).build();
    }

    @Test
    @DisplayName("GET /v1/servico/listar/{id} deve retornar 200")
    void deveListarServico() throws Exception {
        ServicoModel servico = new ServicoModel();
        servico.setId(1L);
        servico.setNome("Corte");
        servico.setValor(50.0);
        when(servicoService.ListaID(1L)).thenReturn(List.of(servico));

        mockMvc.perform(get("/v1/servico/listar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte"))
                .andExpect(jsonPath("$[0].valor").value(50.0));
    }

    @Test
    @DisplayName("POST /v1/servico/criar deve retornar 200")
    void deveCriarServico() throws Exception {
        ServicoModel servico = new ServicoModel();
        servico.setId(1L);
        servico.setNome("Barba");
        servico.setValor(30.0);
        when(servicoService.Criar(any(ServicoModel.class))).thenReturn(servico);

        mockMvc.perform(post("/v1/servico/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Barba"));
    }

    @Test
    @DisplayName("DELETE /v1/servico/deletar/{id} deve retornar 200")
    void deveDeletarServico() throws Exception {
        mockMvc.perform(delete("/v1/servico/deletar/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /v1/servico/atualizar/{id} deve retornar 200")
    void deveAtualizarServico() throws Exception {
        ServicoModel servico = new ServicoModel();
        servico.setId(1L);
        servico.setNome("Corte + Barba");
        servico.setValor(70.0);
        when(servicoService.Alterar(any(ServicoModel.class), eq(1L))).thenReturn(servico);

        mockMvc.perform(put("/v1/servico/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte + Barba"));
    }
}
