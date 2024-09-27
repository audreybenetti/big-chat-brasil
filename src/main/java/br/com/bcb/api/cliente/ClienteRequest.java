package br.com.bcb.api.cliente;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ClienteRequest {
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private String nomeResponsavel;
    private String cpfResponsavel;
}
