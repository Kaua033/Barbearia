package Minha.Barbearia.Servico;

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
 * Teste unitário do servicoService.
 *
 * Usamos Mockito para isolar o service do banco de dados.
 * A anotação @ExtendWith(MockitoExtension.class) ativa o Mockito.
 */
@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private servicoService servicoService;

    private ServicoModel servicoModel;

    @BeforeEach
    void setUp() {
        servicoModel = new ServicoModel();
        servicoModel.setId(1L);
        servicoModel.setNome("Corte de cabelo");
        servicoModel.setValor(50.0);
    }

    @Test
    @DisplayName("Deve listar serviço por ID com sucesso")
    void deveListarServicoPorId() {
        // Configura o mock: findAllById retorna Optional com o serviço
        when(servicoRepository.findAllById(1L))
                .thenReturn(Optional.of(servicoModel));

        List<ServicoModel> resultado = servicoService.ListaID(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Corte de cabelo", resultado.get(0).getNome());
        assertEquals(50.0, resultado.get(0).getValor());
    }

    @Test
    @DisplayName("Deve retornar lista com null quando serviço não existe")
    void deveRetornarListaComNullQuandoNaoExiste() {
        // findAllById retorna Optional.empty() — serviço não encontrado
        when(servicoRepository.findAllById(99L)).thenReturn(Optional.empty());

        List<ServicoModel> resultado = servicoService.ListaID(99L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNull(resultado.get(0)); // O método retorna [null] quando não encontra
    }

    @Test
    @DisplayName("Deve criar serviço com sucesso")
    void deveCriarServico() {
        when(servicoRepository.save(any(ServicoModel.class))).thenReturn(servicoModel);

        ServicoModel resultado = servicoService.Criar(servicoModel);

        assertNotNull(resultado);
        assertEquals("Corte de cabelo", resultado.getNome());
        verify(servicoRepository, times(1)).save(servicoModel);
    }

    @Test
    @DisplayName("Deve deletar serviço por ID")
    void deveDeletarServico() {
        servicoService.Deleçao(1L);

        verify(servicoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve atualizar serviço quando ID existe")
    void deveAtualizarServico() {
        ServicoModel modelAtualizado = new ServicoModel();
        modelAtualizado.setNome("Corte degradê");
        modelAtualizado.setValor(60.0);

        // Mock do findById retornando o serviço original
        when(servicoRepository.findAllById(1L)).thenReturn(Optional.of(servicoModel));
        // Mock do save retornando o modelo salvo
        when(servicoRepository.save(any(ServicoModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ServicoModel resultado = servicoService.Alterar(modelAtualizado, 1L);

        assertNotNull(resultado);
        // O service seta o ID antes de salvar
        assertEquals(1L, resultado.getId());
        assertEquals("Corte degradê", resultado.getNome());
    }

    @Test
    @DisplayName("Deve retornar null ao atualizar serviço inexistente")
    void deveRetornarNullAoAtualizarInexistente() {
        when(servicoRepository.findAllById(99L)).thenReturn(Optional.empty());

        ServicoModel resultado = servicoService.Alterar(servicoModel, 99L);

        assertNull(resultado);
    }
}
