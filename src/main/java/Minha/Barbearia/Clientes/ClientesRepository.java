package Minha.Barbearia.Clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientesRepository extends JpaRepository<ClienteModel,Long> {

    ClienteModel findByNome(String nome);

    ClienteModel findByTelefone(String telefone);
}