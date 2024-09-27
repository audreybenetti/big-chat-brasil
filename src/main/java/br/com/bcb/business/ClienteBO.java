package br.com.bcb.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ClienteBO {
    private String nomeResponsavel;
    private String email;
    private Integer telefone;
    private String cpf;
    private String cnpj;
    private String nomeEmpresa;
}
