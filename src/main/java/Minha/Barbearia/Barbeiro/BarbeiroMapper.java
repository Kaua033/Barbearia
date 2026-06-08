package Minha.Barbearia.Barbeiro;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface BarbeiroMapper {


  BarbeiroModel map(BarbeiroDTO DTO);

    BarbeiroDTO map(BarbeiroModel model);

}
