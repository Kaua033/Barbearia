package Minha.Barbearia.Barbeiro;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/*
 * Mapper MapStruct para Barbeiro.
 *
 * Converte entre BarbeiroDTO e BarbeiroModel.
 * O MapStruct gera a implementação automaticamente.
 */
@Mapper(componentModel = "spring")
public interface BarbeiroMapper {

    BarbeiroModel map(BarbeiroDTO DTO);

    BarbeiroDTO map(BarbeiroModel model);

}
