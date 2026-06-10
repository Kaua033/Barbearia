package Minha.Barbearia.Agendamento;

import Minha.Barbearia.Barbeiro.BarbeiroModel;
import Minha.Barbearia.Barbeiro.BarbeiroRepository;
import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Clientes.ClientesRepository;
import Minha.Barbearia.Servico.ServicoModel;
import Minha.Barbearia.Servico.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste unitário do AgendamentoService.
 *
 * O AgendamentoService depende de 4 repositories:
 * agendamentoRepository, barbeiroRepository, clientesRepository, servicoRepository.
 * Todos são mockados para isolar a lógica do service.
 */
@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgedamentoModel agendamentoModel;
    private AgedamentoDTO agendamentoDTO;
    private BarbeiroModel barbeiroModel;
    private ClienteModel clienteModel;
    private ServicoModel servicoModel;

    @BeforeEach
    void setUp() {
        // ─── Monta os objetos necessários ──────────────────

        barbeiroModel = new BarbeiroModel();
        barbeiroModel.setId(1L);
        barbeiroModel.setNome("Carlos");

        clienteModel = new ClienteModel();
        clienteModel.setId(1L);
        clienteModel.setNome("João");

        servicoModel = new ServicoModel();
        servicoModel.setId(1L);
        servicoModel.setNome("Corte");
        servicoModel.setValor(50.0);

        agendamentoModel = new AgedamentoModel();
        agendamentoModel.setId(1L);
        agendamentoModel.setBarbeiroModel(barbeiroModel);
        agendamentoModel.setClienteModel(clienteModel);
        agendamentoModel.setServicoModel(servicoModel);
        agendamentoModel.setDataHora(LocalDateTime.of(2026, 6, 15, 14, 0));
        agendamentoModel.setStatus(Status.CONFIRMADO);

        agendamentoDTO = new AgedamentoDTO(
                1L,        // clienteId
                1L,        // barbeiroId
                1L,        // servicoId
                LocalDateTime.of(2026, 6, 15, 14, 0),
                "CONFIRMADO"
        );
    }

    @Test
    @DisplayName("Deve listar agendamento por ID")
    void deveListarAgendamento() {
        when(agendamentoRepository.findById(1L))
                .thenReturn(Optional.of(agendamentoModel));

        List<AgedamentoModel> resultado = agendamentoService.Listar(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(Status.CONFIRMADO, resultado.get(0).getStatus());
    }

    @Test
    @DisplayName("Deve retornar lista com null quando agendamento não existe")
    void deveRetornarListaComNullQuandoNaoExiste() {
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());

        List<AgedamentoModel> resultado = agendamentoService.Listar(99L);

        assertNotNull(resultado);
        assertNull(resultado.get(0));
    }

    @Test
    @DisplayName("Deve criar agendamento com sucesso")
    void deveCriarAgendamento() {
        // Mock: cada repository.findByID retorna a entidade correspondente
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiroModel));
        when(clientesRepository.findById(1L)).thenReturn(Optional.of(clienteModel));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servicoModel));
        // Mock: save retorna o agendamento salvo
        when(agendamentoRepository.save(any(AgedamentoModel.class))).thenReturn(agendamentoModel);

        AgedamentoModel resultado = agendamentoService.Criar(agendamentoDTO);

        assertNotNull(resultado);
        assertEquals(Status.CONFIRMADO, resultado.getStatus());
        assertEquals("João", resultado.getClienteModel().getNome());
        assertEquals("Carlos", resultado.getBarbeiroModel().getNome());

        // Verifica que os 3 finds e 1 save foram chamados
        verify(barbeiroRepository, times(1)).findById(1L);
        verify(clientesRepository, times(1)).findById(1L);
        verify(servicoRepository, times(1)).findById(1L);
        verify(agendamentoRepository, times(1)).save(any(AgedamentoModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar agendamento com barbeiro inexistente")
    void deveLancarExcecaoQuandoBarbeiroNaoExiste() {
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());

        AgedamentoDTO dtoInvalido = new AgedamentoDTO(
                1L, 99L, 1L,
                LocalDateTime.now(), "CONFIRMADO"
        );

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> agendamentoService.Criar(dtoInvalido));

        assertEquals("Barbeiro não encontrado!", ex.getMessage());
    }

    @Test
    @DisplayName("Deve deletar agendamento por ID")
    void deveDeletarAgendamento() {
        agendamentoService.DELEÇAO(1L);
        verify(agendamentoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve atualizar agendamento quando ID existe")
    void deveAtualizarAgendamento() {
        AgedamentoModel modelAtualizado = new AgedamentoModel();
        modelAtualizado.setStatus(Status.CONCLUIDO);

        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoModel));
        when(agendamentoRepository.save(any(AgedamentoModel.class))).thenAnswer(inv -> inv.getArgument(0));

        AgedamentoModel resultado = agendamentoService.Atualizar(modelAtualizado, 1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(Status.CONCLUIDO, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve retornar null ao atualizar agendamento inexistente")
    void deveRetornarNullAoAtualizarInexistente() {
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());

        AgedamentoModel resultado = agendamentoService.Atualizar(agendamentoModel, 99L);

        assertNull(resultado);
    }
}
