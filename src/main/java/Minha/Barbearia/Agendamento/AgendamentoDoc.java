package Minha.Barbearia.Agendamento;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AgendamentoDoc {

    @Operation(
            summary = "Ver um agendamento",
            description = "Recebe um id que representa um na tabela "
    )
    @ApiResponse(responseCode = "201", description = "Agenda listada com suscesso")
    @ApiResponse(responseCode = "400", description = "Erro na Agenda listada ")
    ResponseEntity<List<AgedamentoModel>> Ver(@PathVariable long id);


    @Operation(
            summary = "Cria um agendamento",
            description = "Recebe paramentros e cria um agendamento "
    )
    @ApiResponse(responseCode = "201", description = "Agenda criada com suscesso")
    @ApiResponse(responseCode = "400", description = "Erro na Agenda ")
    ResponseEntity<List<AgedamentoModel>> Ver(@RequestBody AgedamentoDTO dto);


    @Operation(
            summary = "deleta um agendamento",
            description = "Recebe paramentros e Deleta um agendamento "
    )
    @ApiResponse(responseCode = "201", description = "Agenda deletada com suscesso")
    @ApiResponse(responseCode = "400", description = "Erro na Deleção de Agenda ")
    void delecao(@PathVariable Long id);


    @Operation(
            summary = "Atualiza um agendamento",
            description = "Recebe paramentros e Atualiza infos do agendamento "
    )
    @ApiResponse(responseCode = "201", description = "Agenda Atualizada com suscesso")
    @ApiResponse(responseCode = "400", description = "Erro na Atualização Agenda")
    ResponseEntity<AgedamentoModel> ATUALIZAR(@RequestBody AgedamentoModel agedamentoModel, @PathVariable Long id);
}