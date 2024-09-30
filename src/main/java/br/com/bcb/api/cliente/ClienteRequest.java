package br.com.bcb.api.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ClienteRequest {
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String nome;

    @Email(message = "E-mail deve ser válido.")
    @NotBlank(message = "E-mail é obrigatório.")
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos.")
    private String telefone;

    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos numéricos.")
    private String cnpj;

    @NotBlank(message = "Nome do responsável é obrigatório.")
    @Size(max = 100, message = "Nome do responsável deve ter no máximo 100 caracteres.")
    private String nomeResponsavel;

    @Pattern(regexp = "\\d{11}", message = "CPF do responsável deve ter 11 dígitos numéricos.")
    private String cpfResponsavel;
}
