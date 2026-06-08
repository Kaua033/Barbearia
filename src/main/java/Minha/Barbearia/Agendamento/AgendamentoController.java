package Minha.Barbearia.Agendamento;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("v1/agendamento")
public class AgendamentoController implements  AgendamentoDoc {

private  AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping("/lsitar/{id}")
    public ResponseEntity<List<AgedamentoModel>> Ver(@PathVariable long id){
  var listV =  agendamentoService.Listar(id);
    return ResponseEntity.ok(listV) ;
}

    @PostMapping("/criar")
public ResponseEntity<List<AgedamentoModel>> Ver(@RequestBody AgedamentoDTO dto){
        var CriarV = agendamentoService.Criar(dto);
        return ResponseEntity.ok(Collections.singletonList(CriarV));
}

@DeleteMapping("/deletar/{id}")
    public void delecao(@PathVariable Long id){
        agendamentoService.DELEÇAO(id);
}


@PutMapping("/atualizar/{id}")
    public ResponseEntity<AgedamentoModel> ATUALIZAR(@RequestBody AgedamentoModel agedamentoModel, @PathVariable Long id){
        var ATU = agendamentoService.Atualizar(agedamentoModel, id);
        return ResponseEntity.ok(ATU);
}

}
