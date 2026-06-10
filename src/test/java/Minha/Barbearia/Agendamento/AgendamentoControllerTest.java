package Minha.Barbearia.Agendamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste do AgendamentoController com MockMvc standalone.
 */
@ExtendWith(MockitoExtension.class)
class AgendamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AgendamentoService agendamentoService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    /*
     * Registrar JavaTimeModule para suportar serialização de LocalDateTime.
     * O Jackson não serializa java.time por padrão.
     */
    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();
    }

    @Test
    @DisplayName("GET /v1/agendamento/lsitar/{id} deve retornar 200")
    void deveListarAgendamento() throws Exception {
        AgedamentoModel agendamento = new AgedamentoModel();
        agendamento.setId(1L);
        agendamento.setStatus(Status.CONFIRMADO);
        agendamento.setDataHora(LocalDateTime.of(2026, 6, 15, 14, 0));

        when(agendamentoService.Listar(1L)).thenReturn(List.of(agendamento));

        mockMvc.perform(get("/v1/agendamento/lsitar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMADO"));
    }

    @Test
    @DisplayName("POST /v1/agendamento/criar deve retornar 200")
    void deveCriarAgendamento() throws Exception {
        AgedamentoDTO dto = new AgedamentoDTO(
                1L, 1L, 1L,
                LocalDateTime.of(2026, 6, 15, 14, 0),
                "CONFIRMADO"
        );

        AgedamentoModel agendamento = new AgedamentoModel();
        agendamento.setId(1L);
        agendamento.setStatus(Status.CONFIRMADO);

        when(agendamentoService.Criar(any(AgedamentoDTO.class))).thenReturn(agendamento);

        mockMvc.perform(post("/v1/agendamento/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMADO"));
    }

    @Test
    @DisplayName("DELETE /v1/agendamento/deletar/{id} deve retornar 200")
    void deveDeletarAgendamento() throws Exception {
        mockMvc.perform(delete("/v1/agendamento/deletar/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /v1/agendamento/atualizar/{id} deve retornar 200")
    void deveAtualizarAgendamento() throws Exception {
        AgedamentoModel agendamento = new AgedamentoModel();
        agendamento.setId(1L);
        agendamento.setStatus(Status.CONCLUIDO);

        when(agendamentoService.Atualizar(any(AgedamentoModel.class), eq(1L))).thenReturn(agendamento);

        mockMvc.perform(put("/v1/agendamento/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONCLUIDO"));
    }
}
