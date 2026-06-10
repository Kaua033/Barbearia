package Minha.Barbearia.SecurityConfig;

import Minha.Barbearia.Clientes.ClienteDTO;
import Minha.Barbearia.Clientes.ClienteModel;
import Minha.Barbearia.Clientes.ClientesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller de autenticação (registro e login).
 *
 * Endpoints: /auth/register e /auth/login
 *
 * Diferente do ClientesController, este controller lida com senhas:
 * - No registro, a senha é criptografada com BCrypt antes de salvar
 * - No login, a senha é verificada com BCrypt.matches()
 */
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(ClientesRepository clientesRepository, PasswordEncoder passwordEncoder) {
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * POST /auth/register
     *
     * Cria um novo usuário (cliente).
     * A senha é criptografada com BCrypt antes de ir para o banco.
     * Retorna HTTP 201 com a mensagem "usuario criado".
     */
    @PostMapping("/register")
    public ResponseEntity<?> Registro(@RequestBody ClienteDTO clienteDTO) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(clienteDTO.nome());
        clienteModel.setTelefone(clienteDTO.telefone());
        clienteModel.setSenha(passwordEncoder.encode(clienteDTO.senha()));
        clientesRepository.save(clienteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("usuario criado");
    }

    /*
     * POST /auth/login
     *
     * Autentica um usuário por nome (ou telefone) + senha.
     *
     * Fluxo:
     * 1. Busca cliente por nome
     * 2. Se não achar, tenta por telefone
     * 3. Se não achar em nenhum dos dois → 401
     * 4. Verifica senha com BCrypt.matches()
     * 5. Se inválida → 401
     * 6. Se ok → retorna ClienteDTO com id, nome, telefone (sem senha)
     */
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody ClienteDTO clienteDTO) {
        // Tenta encontrar por nome primeiro
        ClienteModel cliente = clientesRepository.findByNome(clienteDTO.nome());
        // Se não achou, tenta por telefone (o campo "nome" pode ser telefone)
        if (cliente == null) {
            cliente = clientesRepository.findByTelefone(clienteDTO.nome());
        }
        // Cliente não encontrado → 401
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("usuario ou senha invalidos");
        }
        // Verifica se a senha informada bate com a senha criptografada no banco
        boolean senhaValida = passwordEncoder.matches(clienteDTO.senha(), cliente.getPassword());
        if (!senhaValida) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("usuario ou senha invalidos");
        }
        // Sucesso: retorna dados do cliente (senha não é inclusa por segurança)
        return ResponseEntity.ok(new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getTelefone(), null));
    }

}
