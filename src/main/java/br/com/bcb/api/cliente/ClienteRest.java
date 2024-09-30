package br.com.bcb.api.cliente;

import br.com.bcb.business.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/clientes")
@RestController
@CrossOrigin(origins = "*")
public class ClienteRest {

    private final ClienteService clienteService;

    @Operation(description = "Cadastra um novo cliente.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<ClienteResponse> cadastrarCliente(@Valid @RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.ok(clienteService.salvarCliente(clienteRequest));
    }

    @Operation(description = "Consulta dados do cliente.")
    @GetMapping("/{email}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<ClienteResponse> consultarCliente(@PathVariable String email){
        return ResponseEntity.ok(clienteService.buscarClientePorEmail(email));
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

    @Operation(description = "Consulta clientes")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<List<ClienteResponse>> consultarClientes(){
        return ResponseEntity.ok(clienteService.buscarClientes());
    }

    @Operation(description = "Alterar plano do cliente.")
    @PostMapping("/{cnpj}/planos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Void> atualizarPlano(@PathVariable String cnpj,
                                               @RequestParam Double saldo,
                                               @RequestParam String tipoPlanoEnum){
        clienteService.atualizarDadosPlano(tipoPlanoEnum, saldo, cnpj);
        return ResponseEntity.ok().build();
    }
}
