package Minha.Barbearia.Barbeiro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Barbeiro")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class BarbeiroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome")
    private String   nome;

    @Column(name = "telefone")
    private String telefone;



    public  Long barbeiro(){
        return  id;
    }


}
