package br.com.bcb.api.cliente;

import br.com.bcb.business.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RequestMapping(path = "/clientes")
@RestController
public class ClienteRest {

    private final ClienteService clienteService;

    @Operation(description = "Cadastra um novo cliente.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.ok(clienteService.salvarCliente(clienteRequest));
    }

    @Operation(description = "Consulta dados do cliente.")
    @GetMapping("/{cnpj}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<ClienteResponse> consultarCliente(@PathVariable String cnpj){
        return ResponseEntity.ok(clienteService.buscarClientePorCnpj(cnpj));
    }

    @Operation(description = "Consulta saldo do cliente.")
    @GetMapping("/{cnpj}/creditos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Double> consultarCreditos(@PathVariable String cnpj){
        return ResponseEntity.ok(clienteService.consultarSaldo(cnpj));
    }

    @Operation(description = "Inclui créditos para um cliente.")
    @PostMapping("/{cnpj}/creditos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Void> atualizarCreditos(@PathVariable String cnpj,
                                                  @RequestParam BigDecimal quantidadeCredito){
        clienteService.atualizarSaldo(quantidadeCredito, cnpj);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Alterar plano do cliente.")
    @PostMapping("/{cnpj}/planos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Void> atualizarPlano(@PathVariable String cnpj,
                                                  @RequestParam TipoPlanoEnum tipoPlanoEnum){
        clienteService.atualizarPlano(tipoPlanoEnum.getDescricao(), cnpj);
        return ResponseEntity.ok().build();
    }
}
