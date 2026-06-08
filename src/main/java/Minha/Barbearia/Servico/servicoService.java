package Minha.Barbearia.Servico;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class servicoService {

    /*
     * Implementaçaode crud basico
     *
     * */
    private ServicoRepository servicoRepository;

    public servicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<ServicoModel> ListaID(Long id) {
       Optional<ServicoModel>LISTAid = servicoRepository.findAllById(id);
        return Collections.singletonList(LISTAid.orElse(null));
    }

    public ServicoModel Criar(ServicoModel servicoModel) {
        return servicoRepository.save(servicoModel);
    }

    public void Deleçao(long id) {
        servicoRepository.deleteById(id);
    }


    public ServicoModel Alterar( ServicoModel servicoModelA, Long id) {
        Optional<ServicoModel> SRexist = servicoRepository.findAllById(id);
        if (SRexist.isPresent()) {
            servicoModelA.setId(id);
            return servicoRepository.save(servicoModelA);

        }
    return null;
    }


}
