package Minha.Barbearia.Clientes;

import org.mapstruct.Mapper;

/*
 * Mapper MapStruct para Cliente.
 *
 * Converte automaticamente entre ClienteDTO e ClienteModel.
 * O MapStruct gera a implementação em tempo de compilação.
 *
 * componentModel = "spring" → o mapper vira um bean Spring
 * e pode ser injetado com @Autowired.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteModel map(ClienteDTO clienteDTO);

    ClienteDTO map(ClienteModel clienteModel);
}
