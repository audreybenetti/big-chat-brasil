package br.com.bcb.api.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMensagemEnum {
    SMS,
    WHATSAPP
}
