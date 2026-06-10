package Minha.Barbearia.Servico;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
 * Service de Serviço.
 *
 * Diferente dos outros services, este trabalha diretamente com
 * a entidade ServicoModel em vez de DTO (sem mapper).
 */
@Service
public class servicoService {

    private ServicoRepository servicoRepository;

    public servicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    /*
     * Lista serviço por ID.
     * Retorna lista com 1 elemento (ou null se não existir).
     */
    public List<ServicoModel> ListaID(Long id) {
        Optional<ServicoModel> LISTAid = servicoRepository.findAllById(id);
        return Collections.singletonList(LISTAid.orElse(null));
    }

    /*
     * Cria um serviço.
     */
    public ServicoModel Criar(ServicoModel servicoModel) {
        return servicoRepository.save(servicoModel);
    }

    /*
     * Deleta serviço por ID.
     */
    public void Deleçao(long id) {
        servicoRepository.deleteById(id);
    }

    /*
     * Atualiza serviço existente.
     * Se o ID não existir, retorna null.
     */
    public ServicoModel Alterar(ServicoModel servicoModelA, Long id) {
        Optional<ServicoModel> SRexist = servicoRepository.findAllById(id);
        if (SRexist.isPresent()) {
            servicoModelA.setId(id);
            return servicoRepository.save(servicoModelA);
        }
        return null;
    }

}
