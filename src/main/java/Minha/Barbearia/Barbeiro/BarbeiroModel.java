package Minha.Barbearia.Barbeiro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Entidade JPA que representa a tabela "barbeiro".
 * Cada barbeiro tem nome e telefone.
 */
@Table(name = "Barbeiro")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class BarbeiroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    public Long barbeiro() {
        return id;
    }

}
