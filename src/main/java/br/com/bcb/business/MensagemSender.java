package br.com.bcb.business;

import br.com.bcb.api.TipoMensagemEnum;

public interface MensagemSender {
    void enviar(String telefoneRemetente, String telefoneDestino, String mensagem);
    TipoMensagemEnum getTipoMensagem();
}
