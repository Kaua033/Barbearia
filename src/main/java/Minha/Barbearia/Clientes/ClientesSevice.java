package Minha.Barbearia.Clientes;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientesSevice {


private ClientesRepository clientesRepository;
private ClienteMapper clienteMapper;

    public ClientesSevice(ClientesRepository clientesRepository, ClienteMapper clienteMapper) {
        this.clientesRepository = clientesRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteDTO> LISTARid(Long id){
    Optional<ClienteModel> clienteModel = clientesRepository.findById(id);
        return Collections.singletonList(clienteModel
                .map(clienteMapper::map)
                .orElseThrow( () -> new RuntimeException("cliente nao encontrado")));
}


 public ClienteDTO CRIAR(ClienteDTO clienteDTO){
 ClienteModel clienteModelV  = clienteMapper.map(clienteDTO);
     clienteModelV = clientesRepository.save(clienteModelV);
 return clienteMapper.map(clienteModelV);
 }


 public  void DELECAO(Long id){
        clientesRepository.deleteById(id);
 }


 public ClienteDTO AUTALIZAR(ClienteDTO clienteDTO,Long id){
     Optional<ClienteModel> VERIC = clientesRepository.findById(id);
     if (VERIC.isPresent()){
       ClienteModel clienteAtualizado = clienteMapper.map(clienteDTO);
       clienteAtualizado.setId(id);
ClienteModel clienteSalvo = clientesRepository.save(clienteAtualizado);
         return clienteMapper.map(clienteSalvo);
     }
 return null;
    }
}
