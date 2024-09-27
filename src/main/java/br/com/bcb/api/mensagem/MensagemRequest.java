package br.com.bcb.api.mensagem;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MensagemRequest {
    private String cnpjCliente;
    private String telefone;
    private String mensagem;
    private TipoMensagemEnum tipoMensagem;
}
