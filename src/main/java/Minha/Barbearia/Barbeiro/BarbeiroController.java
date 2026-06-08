package Minha.Barbearia.Barbeiro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/barbeiro")
public class BarbeiroController  implements BarbeiroDoc{

private BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @PostMapping("/criar")
public ResponseEntity<BarbeiroDTO>criar(@RequestBody BarbeiroDTO barbeiroDTO){
 var teste = barbeiroService.Criar(barbeiroDTO);
    return ResponseEntity.ok().build();
}

@GetMapping("/listar/{id}")
    public ResponseEntity<List<BarbeiroDTO>> ver(@PathVariable Long id){
        List<BarbeiroDTO> ver = barbeiroService.mostrar(id);
        return  ResponseEntity.ok(ver);
}


    @DeleteMapping("/delecao/{id}")
    public void DELEÇAO(@PathVariable Long id){
        barbeiroService.DELEÇAO(id);
}


@PutMapping("/atualizar/{id}")
    public ResponseEntity<BarbeiroDTO>atualizar(@RequestBody BarbeiroDTO barbeiroDTO,@PathVariable Long id){
 var ATU = barbeiroService.AUTALIZAR(barbeiroDTO, id);
return ResponseEntity.ok(ATU);
    }

}
