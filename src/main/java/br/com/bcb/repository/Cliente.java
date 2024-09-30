package br.com.bcb.repository;

import br.com.bcb.api.cliente.ClienteRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(name = "nome_responsavel", nullable = false)
    private String nomeResponsavel;

    @Column(name = "cpf_responsavel", nullable = false)
    private String cpfResponsavel;

    @Column(name = "plano")
    private String plano;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "credito_utilizado")
    private Double creditoUtilizado;

    @Column(name = "limite_credito")
    private Double limiteCredito;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    public static Cliente of(ClienteRequest request) {
        return Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .cnpj(request.getCnpj())
                .nomeResponsavel(request.getNomeResponsavel())
                .cpfResponsavel(request.getCpfResponsavel())
                .dataCadastro(LocalDate.now())
                .build();
    }
}
