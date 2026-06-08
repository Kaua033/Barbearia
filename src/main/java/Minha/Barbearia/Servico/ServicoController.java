package Minha.Barbearia.Servico;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/servico")
public class ServicoController {


private servicoService  servicoService;

    public ServicoController(servicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<ServicoModel>> listar(@PathVariable Long id){
       var listado = servicoService.ListaID(id);
        return ResponseEntity.ok(listado);
}

@PostMapping("/criar")
    public ResponseEntity criar(@RequestBody ServicoModel servicoModel){
        var Criar =  servicoService.Criar(servicoModel);
        return ResponseEntity.ok(Criar);
}

@DeleteMapping("/deletar/{id}")
    public void Deleçao(@PathVariable long id){
    servicoService.Deleçao(id);
}


@PutMapping("/atualizar/{id}")
    public ResponseEntity Atualizar(@PathVariable Long id,@RequestBody ServicoModel servicoModel){
        var atualizar = servicoService.Alterar(servicoModel,id);
        return ResponseEntity.ok(atualizar);
}

 }

