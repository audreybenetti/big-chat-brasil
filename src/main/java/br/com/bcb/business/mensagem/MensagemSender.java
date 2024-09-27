package br.com.bcb.business.mensagem;

import br.com.bcb.api.mensagem.TipoMensagemEnum;

public interface MensagemSender {
    void enviar(String telefoneRemetente, String telefoneDestino, String mensagem);
    TipoMensagemEnum getTipoMensagem();
}
