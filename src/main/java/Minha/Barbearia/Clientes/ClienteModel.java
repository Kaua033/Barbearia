package Minha.Barbearia.Clientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * Entidade JPA que representa a tabela "cliente".
 *
 * Implementa UserDetails do Spring Security para servir como
 * usuário do sistema. Isso permite autenticação via banco de dados.
 *
 * Lombok @Data gera getters, setters, toString, equals, hashCode.
 * Lombok @NoArgsConstructor gera construtor vazio (obrigatório JPA).
 * Lombok @AllArgsConstructor gera construtor com todos os campos.
 */
@Table(name = "Cliente")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class ClienteModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "senha")
    private String senha;

    /*
     * Método auxiliar para obter o ID.
     */
    public Long clienteId() {
        return id;
    }

    // ─── Métodos do UserDetails ──────────────────────────
    // O Spring Security usa estes métodos para autenticação.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // sem papéis/permissões por enquanto
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    /*
     * Os métodos abaixo retornam true para indicar que a conta
     * não está expirada, bloqueada ou desabilitada.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
