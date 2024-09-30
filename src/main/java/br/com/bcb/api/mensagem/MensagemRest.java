package br.com.bcb.api.mensagem;

import br.com.bcb.business.mensagem.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/mensagens")
@RestController
public class MensagemRest {

    private final MensagemService mensagemService;

    @Operation(description = "Envia uma mensagem.")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Void> enviarMensagem(@RequestBody MensagemRequest mensagemRequest){
        mensagemService.processarMensagem(mensagemRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Envia mensagens.")
    @PostMapping("/lote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição executada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro não esperado")
    })
    public ResponseEntity<Void> enviarMensagemEmLote(@RequestBody MensagemBatchRequest mensagemBatchRequest){
        mensagemService.processarMensagensEmLote(mensagemBatchRequest);
        return ResponseEntity.ok().build();
    }
}
