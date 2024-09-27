package br.com.bcb.api;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MensagemBatchRequest {
    private String cnpjCliente;
    private List<String> telefones;
    private String mensagem;
    private TipoMensagemEnum tipoMensagem;
}
