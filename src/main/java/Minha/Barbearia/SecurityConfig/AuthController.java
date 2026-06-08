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

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(ClientesRepository clientesRepository, PasswordEncoder passwordEncoder) {
        this.clientesRepository = clientesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> Registro(@RequestBody ClienteDTO clienteDTO){
        ClienteModel clienteModel = new ClienteModel();
    clienteModel.setNome(clienteDTO.nome());
        clienteModel.setSenha(passwordEncoder.encode(clienteDTO.senha()));
     clientesRepository.save(clienteModel);
     return ResponseEntity.status(HttpStatus.CREATED).body("usuario criado");
    }

}
