package Minha.Barbearia.Servico;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicoRepository  extends JpaRepository<ServicoModel,Long> {

    Optional<ServicoModel> findAllById(Long id);
}
