package Minha.Barbearia.Barbeiro;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Service de Barbeiro.
 *
 * Segue o mesmo padrão do ClientesSevice:
 * usa BarbeiroMapper (MapStruct) para conversão DTO <-> Model.
 */
@Service
public class BarbeiroService {

    private BarbeiroRepository barbeiroRepository;
    private BarbeiroMapper barbeiroMapper;

    public BarbeiroService(BarbeiroRepository barbeiroRepository, BarbeiroMapper barbeiroMapper) {
        this.barbeiroRepository = barbeiroRepository;
        this.barbeiroMapper = barbeiroMapper;
    }

    /*
     * Busca barbeiro por ID.
     * Usa stream().map() para converter Optional<Model> em List<DTO>.
     * Se não encontrar, retorna lista vazia.
     */
    public List<BarbeiroDTO> mostrar(Long id) {
        Optional<BarbeiroModel> barbeiroModels = barbeiroRepository.findById(id);
        return barbeiroModels
                .stream()
                .map(barbeiroMapper::map).collect(Collectors.toList());
    }

    /*
     * Deleta barbeiro por ID.
     * Usa deleteAllById com Collections.singleton (em vez de deleteById).
     */
    public void DELEÇAO(Long id) {
        barbeiroRepository.deleteAllById(Collections.singleton(id));
    }

    /*
     * Cria um barbeiro: DTO -> Model -> save -> Model -> DTO.
     */
    public BarbeiroDTO Criar(BarbeiroDTO barbeiroDTO) {
        BarbeiroModel barbeiroModelV = barbeiroMapper.map(barbeiroDTO);
        barbeiroModelV = barbeiroRepository.save(barbeiroModelV);
        return barbeiroMapper.map(barbeiroModelV);
    }

    /*
     * Atualiza barbeiro existente.
     * Se o ID não existir, retorna null.
     */
    public BarbeiroDTO AUTALIZAR(BarbeiroDTO barbeiroDTO, Long id) {
        Optional<BarbeiroModel> VERIC = barbeiroRepository.findById(id);
        if (VERIC.isPresent()) {
            BarbeiroModel ninjaAtualizado = barbeiroMapper.map(barbeiroDTO);
            ninjaAtualizado.setId(id);
            BarbeiroModel barbeiroModel = barbeiroRepository.save(ninjaAtualizado);
            return barbeiroMapper.map(barbeiroModel);
        }
        return null;
    }

}


