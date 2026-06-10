package Minha.Barbearia.Barbeiro;

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
 * Teste unitário do BarbeiroService.
 *
 * A anotação @ExtendWith(MockitoExtension.class) ativa o suporte do Mockito.
 * Isso permite usar @Mock e @InjectMocks.
 */
@ExtendWith(MockitoExtension.class)
class BarbeiroServiceTest {

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Mock
    private BarbeiroMapper barbeiroMapper;

    @InjectMocks
    private BarbeiroService barbeiroService;

    private BarbeiroModel barbeiroModel;
    private BarbeiroDTO barbeiroDTO;

    @BeforeEach
    void setUp() {
        barbeiroModel = new BarbeiroModel();
        barbeiroModel.setId(1L);
        barbeiroModel.setNome("Carlos");
        barbeiroModel.setTelefone("11911111111");

        barbeiroDTO = new BarbeiroDTO("Carlos", "11911111111");
    }

    @Test
    @DisplayName("Deve listar barbeiro por ID com sucesso")
    void deveListarBarbeiroPorId() {
        when(barbeiroRepository.findById(1L))
                .thenReturn(Optional.of(barbeiroModel));
        when(barbeiroMapper.map(barbeiroModel)).thenReturn(barbeiroDTO);

        List<BarbeiroDTO> resultado = barbeiroService.mostrar(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).nome());
        verify(barbeiroRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando barbeiro não existe")
    void deveRetornarListaVaziaQuandoBarbeiroNaoExiste() {
        // findById retorna Optional.empty() — barbeiro não encontrado
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());

        List<BarbeiroDTO> resultado = barbeiroService.mostrar(99L);

        // A lista deve estar vazia, não null
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve criar barbeiro com sucesso")
    void deveCriarBarbeiro() {
        when(barbeiroMapper.map(barbeiroDTO)).thenReturn(barbeiroModel);
        when(barbeiroRepository.save(any(BarbeiroModel.class))).thenReturn(barbeiroModel);
        when(barbeiroMapper.map(barbeiroModel)).thenReturn(barbeiroDTO);

        BarbeiroDTO resultado = barbeiroService.Criar(barbeiroDTO);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.nome());
        verify(barbeiroRepository, times(1)).save(barbeiroModel);
    }

    @Test
    @DisplayName("Deve deletar barbeiro por ID")
    void deveDeletarBarbeiro() {
        barbeiroService.DELEÇAO(1L);

        // Verifica que deleteAllById foi chamado com o ID correto
        verify(barbeiroRepository, times(1))
                .deleteAllById(any());
    }

    @Test
    @DisplayName("Deve atualizar barbeiro quando ID existe")
    void deveAtualizarBarbeiro() {
        BarbeiroDTO dtoAtualizado = new BarbeiroDTO("Carlos Silva", "11922222222");
        BarbeiroModel modelAtualizado = new BarbeiroModel();
        modelAtualizado.setId(1L);
        modelAtualizado.setNome("Carlos Silva");
        modelAtualizado.setTelefone("11922222222");

        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiroModel));
        when(barbeiroMapper.map(dtoAtualizado)).thenReturn(modelAtualizado);
        when(barbeiroRepository.save(any(BarbeiroModel.class))).thenReturn(modelAtualizado);
        when(barbeiroMapper.map(modelAtualizado)).thenReturn(dtoAtualizado);

        BarbeiroDTO resultado = barbeiroService.AUTALIZAR(dtoAtualizado, 1L);

        assertNotNull(resultado);
        assertEquals("Carlos Silva", resultado.nome());
        assertEquals("11922222222", resultado.telefone());
    }

    @Test
    @DisplayName("Deve retornar null ao atualizar barbeiro inexistente")
    void deveRetornarNullAoAtualizarInexistente() {
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());

        BarbeiroDTO resultado = barbeiroService.AUTALIZAR(barbeiroDTO, 99L);

        assertNull(resultado);
    }
}
