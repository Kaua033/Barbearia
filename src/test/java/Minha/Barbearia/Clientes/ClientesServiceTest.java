package Minha.Barbearia.Clientes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste unitário do ClientesSevice.
 *
 * Usamos Mockito para simular (mockar) o ClientesRepository e o ClienteMapper.
 * Isso isola a lógica do service do banco de dados real.
 *
 * @ExtendWith(MockitoExtension.class) ativa o Mockito no JUnit 5.
 */
@ExtendWith(MockitoExtension.class)
class ClientesServiceTest {

    // @Mock cria um objeto falso que não executa a lógica real
    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private ClienteMapper clienteMapper;

    // @InjectMocks injeta os mocks acima dentro do ClientesSevice real
    @InjectMocks
    private ClientesSevice clientesSevice;

    private ClienteModel clienteModel;
    private ClienteDTO clienteDTO;

    /**
     * BeforeEach roda antes de cada teste.
     * Aqui montamos os objetos que vários testes reaproveitam.
     */
    @BeforeEach
    void setUp() {
        clienteModel = new ClienteModel();
        clienteModel.setId(1L);
        clienteModel.setNome("João");
        clienteModel.setTelefone("11999999999");
        clienteModel.setSenha("senha123");

        clienteDTO = new ClienteDTO(1L, "João", "11999999999", "senha123");
    }

    // ─── LISTAR por ID ─────────────────────────────────────

    @Test
    @DisplayName("Deve retornar cliente quando ID existe")
    void deveListarClientePorIdComSucesso() {
        // 1. Configuramos o comportamento do mock "findById"
        //    para retornar um Optional com o clienteModel
        when(clientesRepository.findById(1L))
                .thenReturn(Optional.of(clienteModel));

        // 2. Configuramos o mapper para converter Model -> DTO
        when(clienteMapper.map(clienteModel))
                .thenReturn(clienteDTO);

        // 3. Chamamos o método real do service
        List<ClienteDTO> resultado = clientesSevice.LISTARid(1L);

        // 4. Verificamos se o resultado é o esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João", resultado.get(0).nome());

        // 5. Verificamos se o findById foi chamado 1 vez com o ID 1
        verify(clientesRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // findById retorna Optional.empty() — cliente não encontrado
        when(clientesRepository.findById(99L))
                .thenReturn(Optional.empty());

        // O método deve lançar RuntimeException com a mensagem esperada
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clientesSevice.LISTARid(99L));

        assertEquals("cliente nao encontrado", ex.getMessage());
    }

    // ─── CRIAR ─────────────────────────────────────────────

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void deveCriarClienteComSucesso() {
        // 1. Mock: converter DTO -> Model
        when(clienteMapper.map(clienteDTO)).thenReturn(clienteModel);

        // 2. Mock: save retorna o modelo salvo
        when(clientesRepository.save(any(ClienteModel.class)))
                .thenReturn(clienteModel);

        // 3. Mock: converter Model salvo -> DTO de resposta
        when(clienteMapper.map(clienteModel)).thenReturn(clienteDTO);

        // Executa
        ClienteDTO resultado = clientesSevice.CRIAR(clienteDTO);

        // Verifica
        assertNotNull(resultado);
        assertEquals("João", resultado.nome());
        verify(clientesRepository, times(1)).save(any(ClienteModel.class));
    }

    // ─── DELETAR ───────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar cliente por ID")
    void deveDeletarCliente() {
        // O método deleteById é void - só verificamos se foi chamado
        clientesSevice.DELECAO(1L);

        verify(clientesRepository, times(1)).deleteById(1L);
    }

    // ─── ATUALIZAR ─────────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar cliente quando ID existe")
    void deveAtualizarClienteComSucesso() {
        // Dados atualizados
        ClienteDTO dtoAtualizado = new ClienteDTO(1L, "João Silva", "11988888888", "novaSenha");
        ClienteModel modelAtualizado = new ClienteModel();
        modelAtualizado.setId(1L);
        modelAtualizado.setNome("João Silva");
        modelAtualizado.setTelefone("11988888888");
        modelAtualizado.setSenha("novaSenha");

        // findById retorna o cliente existente
        when(clientesRepository.findById(1L)).thenReturn(Optional.of(clienteModel));

        // mapper converte o DTO atualizado para Model
        when(clienteMapper.map(dtoAtualizado)).thenReturn(modelAtualizado);

        // save retorna o model salvo
        when(clientesRepository.save(any(ClienteModel.class))).thenReturn(modelAtualizado);

        // mapper converte o model salvo de volta para DTO
        when(clienteMapper.map(modelAtualizado)).thenReturn(dtoAtualizado);

        ClienteDTO resultado = clientesSevice.AUTALIZAR(dtoAtualizado, 1L);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.nome());
        assertEquals("11988888888", resultado.telefone());

        // Verifica que o ID foi setado antes de salvar
        verify(clienteMapper, times(1)).map(dtoAtualizado);
        verify(clientesRepository, times(1)).save(modelAtualizado);
    }

    @Test
    @DisplayName("Deve retornar null ao atualizar cliente que não existe")
    void deveRetornarNullAoAtualizarClienteInexistente() {
        when(clientesRepository.findById(99L)).thenReturn(Optional.empty());

        ClienteDTO resultado = clientesSevice.AUTALIZAR(clienteDTO, 99L);

        assertNull(resultado);
    }
}
