package Minha.Barbearia.Clientes;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
 * Service de Clientes.
 *
 * Usa o ClienteMapper (MapStruct) para converter entre
 * ClienteDTO (camada web) e ClienteModel (camada de dados).
 * Isso evita expor a entidade JPA diretamente na API.
 */
@Service
public class ClientesSevice {

    private ClientesRepository clientesRepository;
    private ClienteMapper clienteMapper;

    public ClientesSevice(ClientesRepository clientesRepository, ClienteMapper clienteMapper) {
        this.clientesRepository = clientesRepository;
        this.clienteMapper = clienteMapper;
    }

    /*
     * Busca cliente por ID.
     * Se não encontrar, lança RuntimeException.
     * Retorna uma lista com 1 elemento (padrão do projeto).
     */
    public List<ClienteDTO> LISTARid(Long id) {
        Optional<ClienteModel> clienteModel = clientesRepository.findById(id);
        return Collections.singletonList(clienteModel
                .map(clienteMapper::map)
                .orElseThrow(() -> new RuntimeException("cliente nao encontrado")));
    }

    /*
     * Cria um cliente.
     *
     * Fluxo:
     * 1. Converte DTO -> Model (via MapStruct)
     * 2. Salva no banco
     * 3. Converte o Model salvo de volta para DTO (esconde a senha, etc.)
     */
    public ClienteDTO CRIAR(ClienteDTO clienteDTO) {
        ClienteModel clienteModelV = clienteMapper.map(clienteDTO);
        clienteModelV = clientesRepository.save(clienteModelV);
        return clienteMapper.map(clienteModelV);
    }

    /*
     * Deleta cliente por ID.
     */
    public void DELECAO(Long id) {
        clientesRepository.deleteById(id);
    }

    /*
     * Atualiza cliente existente.
     *
     * 1. Verifica se o ID existe
     * 2. Se existir, converte DTO -> Model, seta o ID e salva
     * 3. Converte o resultado de volta para DTO
     * 4. Se não existir, retorna null
     */
    public ClienteDTO AUTALIZAR(ClienteDTO clienteDTO, Long id) {
        Optional<ClienteModel> VERIC = clientesRepository.findById(id);
        if (VERIC.isPresent()) {
            ClienteModel clienteAtualizado = clienteMapper.map(clienteDTO);
            clienteAtualizado.setId(id);
            ClienteModel clienteSalvo = clientesRepository.save(clienteAtualizado);
            return clienteMapper.map(clienteSalvo);
        }
        return null;
    }
}
