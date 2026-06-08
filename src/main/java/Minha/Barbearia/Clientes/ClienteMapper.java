package Minha.Barbearia.Clientes;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteModel map(ClienteDTO clienteDTO);

    ClienteDTO map(ClienteModel clienteModel);
}
