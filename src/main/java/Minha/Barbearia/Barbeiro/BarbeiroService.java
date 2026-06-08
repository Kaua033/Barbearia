package Minha.Barbearia.Barbeiro;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BarbeiroService {

private  BarbeiroRepository barbeiroRepository;
private BarbeiroMapper barbeiroMapper;

    public BarbeiroService(BarbeiroRepository barbeiroRepository, BarbeiroMapper barbeiroMapper) {
        this.barbeiroRepository = barbeiroRepository;
        this.barbeiroMapper = barbeiroMapper;
    }

 public List<BarbeiroDTO> mostrar(Long id){
        Optional<BarbeiroModel> barbeiroModels = barbeiroRepository.findById(id);
        return  barbeiroModels
                .stream()
                .map( barbeiroMapper:: map).collect(Collectors.toList()); }


public void  DELEÇAO(Long id){
        barbeiroRepository.deleteAllById(Collections.singleton(id));
}


public BarbeiroDTO Criar( BarbeiroDTO barbeiroDTO){
BarbeiroModel barbeiroModelV = barbeiroMapper.map(barbeiroDTO);
   barbeiroModelV = barbeiroRepository.save(barbeiroModelV);
   return barbeiroMapper.map(barbeiroModelV);
}

public BarbeiroDTO AUTALIZAR(BarbeiroDTO barbeiroDTO, Long id){
    Optional<BarbeiroModel>VERIC = barbeiroRepository.findById(id);
    if (VERIC.isPresent()){
        BarbeiroModel ninjaAtualizado = barbeiroMapper.map(barbeiroDTO);
     ninjaAtualizado.setId(id);
       BarbeiroModel barbeiroModel = barbeiroRepository.save(ninjaAtualizado);
        return barbeiroMapper.map(barbeiroModel);
    }
return  null;
    }


}


