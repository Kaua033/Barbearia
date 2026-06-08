package Minha.Barbearia.Agendamento;

import Minha.Barbearia.Barbeiro.BarbeiroModel;
import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Servico.ServicoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Table(name = "agendamento")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class AgedamentoModel {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id")
     private Long id;

    @Column(name = "DataHora")
    private LocalDateTime DataHora;

    @Column(name = "Status")
     private Status status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel clienteModel;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoModel servicoModel;


    @ManyToOne
    @JoinColumn(name = "barbeiro_id")
    private BarbeiroModel barbeiroModel;

public Long agdmID(){
    return id;
}
}
