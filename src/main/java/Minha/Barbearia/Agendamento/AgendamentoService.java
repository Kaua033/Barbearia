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

/*
 * Service responsável pela lógica de negócio dos agendamentos.
 *
 * Diferente dos outros services, este depende de 4 repositories
 * porque um agendamento envolve barbeiro, cliente e serviço.
 */
@Service
public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;
    private BarbeiroRepository barbeiroRepository;
    private ClientesRepository clientesRepository;
    private ServicoRepository servicoRepository;

    /*
     * Injeção de dependência via construtor.
     * O Spring fornece automaticamente as 4 dependências.
     */
    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              BarbeiroRepository barbeiroRepository,
                              ClientesRepository clientesRepository,
                              ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.clientesRepository = clientesRepository;
        this.servicoRepository = servicoRepository;
    }

    /*
     * Lista um agendamento pelo ID.
     * Retorna uma lista com 1 elemento (ou null se não existir).
     */
    public List<AgedamentoModel> Listar(Long id) {
        Optional<AgedamentoModel> agedamentoModel = agendamentoRepository.findById(id);
        return Collections.singletonList(agedamentoModel.orElse(null));
    }

    /*
     * Cria um agendamento completo.
     *
     * Passo a passo:
     * 1. Busca barbeiro, cliente e serviço pelos IDs do DTO
     * 2. Se algum não existir, lança RuntimeException
     * 3. Monta o AgedamentoModel com as entidades e dados
     * 4. Salva no banco e retorna
     */
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

    /*
     * Deleta um agendamento pelo ID.
     * Se o ID não existir, o deleteById simplesmente não faz nada.
     */
    public void DELEÇAO(Long id) {
        agendamentoRepository.deleteById(id);
    }

    /*
     * Atualiza um agendamento existente.
     *
     * 1. Busca o agendamento original pelo ID
     * 2. Se existir, sobrescreve o ID no objeto recebido e salva
     * 3. Se não existir, retorna null
     *
     * ATENÇÃO: Isso substitui TODOS os campos do agendamento original.
     */
    public AgedamentoModel Atualizar(AgedamentoModel agedamentoModelV, Long id) {
        Optional<AgedamentoModel> atualizado = agendamentoRepository.findById(id);
        if (atualizado.isPresent()) {
            agedamentoModelV.setId(id);
            return agendamentoRepository.save(agedamentoModelV);
        }
        return null;
    }

}

