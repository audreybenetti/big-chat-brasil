package br.com.bcb.api.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPlanoEnum {
    PRE_PAGO("Pré-pago"),
    POS_PAGO ("Pré-pago");

    private String descricao;
}
