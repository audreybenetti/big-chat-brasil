package br.com.bcb.business;

import br.com.bcb.api.TipoMensagemEnum;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService implements MensagemSender {
    @Override
    public void enviar(String telefoneRemetente, String telefoneDestino, String mensagem) {
        System.out.println("WhatsApp enviado para " + telefoneDestino + " com mensagem: " + mensagem);
    }

    @Override
    public TipoMensagemEnum getTipoMensagem() {
        return TipoMensagemEnum.WHATSAPP;
    }
}
