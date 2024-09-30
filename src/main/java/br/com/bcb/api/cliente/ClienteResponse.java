package br.com.bcb.api.cliente;

import br.com.bcb.repository.Cliente;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ClienteResponse {
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private String nomeResponsavel;
    private String cpfResponsavel;
    private String nomePlano;
    private Double saldo;
    private Double creditoUtilizado;
    private Double limiteCredito;
    private LocalDate dataCadastro;

    public static ClienteResponse of(Cliente entity) {
        return ClienteResponse.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .cnpj(entity.getCnpj())
                .nomeResponsavel(entity.getNomeResponsavel())
                .cpfResponsavel(entity.getCpfResponsavel())
                .nomePlano(entity.getPlano())
                .saldo(entity.getSaldo())
                .limiteCredito(entity.getLimiteCredito())
                .dataCadastro(entity.getDataCadastro())
                .build();
    }
}
