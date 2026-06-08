package Minha.Barbearia.Agendamento;

import Minha.Barbearia.Barbeiro.BarbeiroModel;
import Minha.Barbearia.Barbeiro.BarbeiroRepository;
import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Clientes.ClientesRepository;
import Minha.Barbearia.Servico.ServicoModel;
import Minha.Barbearia.Servico.ServicoRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {
private  AgendamentoRepository agendamentoRepository;

private BarbeiroRepository barbeiroRepository;

private ClientesRepository clientesRepository;

private ServicoRepository servicoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, BarbeiroRepository barbeiroRepository, ClientesRepository clientesRepository, ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.clientesRepository = clientesRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<AgedamentoModel> Listar(Long id){
    Optional<AgedamentoModel> agedamentoModel = agendamentoRepository.findById(id);
    return Collections.singletonList(agedamentoModel.orElse(null));
}

    public AgedamentoModel Criar(AgedamentoDTO dto) {

        BarbeiroModel barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado!"));

        ClienteModel cliente = clientesRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        ServicoModel servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        AgedamentoModel agendamento = new AgedamentoModel();
        agendamento.setBarbeiroModel(barbeiro);
        agendamento.setClienteModel(cliente);
        agendamento.setServicoModel(servico);
        agendamento.setDataHora(dto.dataHora());
        agendamento.setStatus(Status.valueOf(dto.status()));

        return agendamentoRepository.save(agendamento);
    }

public  void  DELEÇAO(Long id){
        agendamentoRepository.deleteById(id);
}

public  AgedamentoModel Atualizar(AgedamentoModel agedamentoModelV, Long id){
        Optional<AgedamentoModel> atualizado =  agendamentoRepository.findById(id);
if (atualizado.isPresent()){
    agedamentoModelV.setId(id);
    return agendamentoRepository.save(agedamentoModelV);
}
    return  null;
    }


}

