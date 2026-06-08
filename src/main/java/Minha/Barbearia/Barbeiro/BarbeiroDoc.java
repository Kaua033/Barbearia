package Minha.Barbearia.Barbeiro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Barbeiro", description = "Gerenciamento de barbeiros")
public interface BarbeiroDoc {

    @Operation(summary = "Cadastrar barbeiro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<BarbeiroDTO> criar(@RequestBody BarbeiroDTO barbeiroDTO);


    @Operation(summary = "Buscar barbeiro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    ResponseEntity<List<BarbeiroDTO>> ver(@PathVariable Long id);


    @Operation(summary = "Deletar barbeiro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    void DELEÇAO(@PathVariable Long id);


    @Operation(summary = "Atualizar barbeiro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    ResponseEntity<BarbeiroDTO> atualizar(@RequestBody BarbeiroDTO barbeiroDTO, @PathVariable Long id);
}