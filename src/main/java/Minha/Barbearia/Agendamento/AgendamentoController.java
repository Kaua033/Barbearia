package Minha.Barbearia.Agendamento;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/*
 * Controller REST de agendamentos.
 *
 * Expõe endpoints para CRUD de agendamentos em /v1/agendamento.
 * Implementa a interface AgendamentoDoc para documentação Swagger.
 */
@RestController
@RequestMapping("v1/agendamento")
public class AgendamentoController implements AgendamentoDoc {

    /*
     * Injeção do service via construtor.
     * O controller delega toda a lógica para o service.
     */
    private AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    /*
     * GET /v1/agendamento/lsitar/{id}
     * Retorna uma lista com o agendamento do ID informado.
     */
    @GetMapping("/lsitar/{id}")
    public ResponseEntity<List<AgedamentoModel>> Ver(@PathVariable long id) {
        var listV = agendamentoService.Listar(id);
        return ResponseEntity.ok(listV);
    }

    /*
     * POST /v1/agendamento/criar
     * Cria um novo agendamento com base no DTO recebido no corpo da requisição.
     * Retorna o agendamento criado dentro de uma lista.
     */
    @PostMapping("/criar")
    public ResponseEntity<List<AgedamentoModel>> Ver(@RequestBody AgedamentoDTO dto) {
        var CriarV = agendamentoService.Criar(dto);
        return ResponseEntity.ok(Collections.singletonList(CriarV));
    }

    /*
     * DELETE /v1/agendamento/deletar/{id}
     * Deleta o agendamento com o ID informado.
     */
    @DeleteMapping("/deletar/{id}")
    public void delecao(@PathVariable Long id) {
        agendamentoService.DELEÇAO(id);
    }

    /*
     * PUT /v1/agendamento/atualizar/{id}
     * Atualiza todos os campos do agendamento com o ID informado.
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AgedamentoModel> ATUALIZAR(@RequestBody AgedamentoModel agedamentoModel, @PathVariable Long id) {
        var ATU = agendamentoService.Atualizar(agedamentoModel, id);
        return ResponseEntity.ok(ATU);
    }

}
