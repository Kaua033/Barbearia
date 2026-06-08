package Minha.Barbearia.Servico;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Servico", description = "Gerenciamento de serviços")
public interface ServicoDoc {

    @Operation(summary = "Buscar serviço por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    ResponseEntity<List<ServicoModel>> listar(@PathVariable Long id);


    @Operation(summary = "Cadastrar serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<ServicoModel> criar(@RequestBody ServicoModel servicoModel);


    @Operation(summary = "Deletar serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    void deletar(Long id);


    @Operation(summary = "Atualizar serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    ResponseEntity<ServicoModel> atualizar(@PathVariable Long id, @RequestBody ServicoModel servicoModel);
}