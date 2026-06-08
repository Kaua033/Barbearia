package Minha.Barbearia.Clientes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cliente")
public class ClientesController implements  ClientesDoc {

private ClientesSevice clientesSevice;

    public ClientesController(ClientesSevice clientesSevice) {
        this.clientesSevice = clientesSevice;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<ClienteDTO>> LISTRA(@PathVariable Long id){
        var listar =clientesSevice.LISTARid(id);
           return ResponseEntity.ok(listar);
    }

@PostMapping("/Criar")
public  ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO){
        var criar = clientesSevice.CRIAR(clienteDTO);
        return ResponseEntity.ok(criar);
}
@DeleteMapping("/deletar/{id}")
    public  void delecao(@PathVariable  Long id){
        clientesSevice.DELECAO(id);
}

@PutMapping("/atualizar/{id}")
    public ResponseEntity<?> ATUALIZAR(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id){
        var autalizar = clientesSevice.AUTALIZAR(clienteDTO, id);
        return ResponseEntity.ok(autalizar);
}

}
