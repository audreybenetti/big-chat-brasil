package br.com.bcb.business.mensagem;

import br.com.bcb.api.cliente.ClienteResponse;
import br.com.bcb.api.cliente.TipoPlanoEnum;
import br.com.bcb.api.mensagem.MensagemBatchRequest;
import br.com.bcb.api.mensagem.MensagemRequest;
import br.com.bcb.api.mensagem.TipoMensagemEnum;
import br.com.bcb.business.ClienteService;
import br.com.bcb.config.exceptions.BusinessException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MensagemService {

    private final ClienteService clienteService;
    private final List<MensagemSender> mensagemSenders;

    private static final double PRECO_POR_MENSAGEM_PRE_PAGO = 0.25;
    private static final double CREDITO_POR_MENSAGEM_POS_PAGO = 1.0;

    public void processarMensagem(MensagemRequest mensagemRequest) {
        ClienteResponse cliente = clienteService.buscarClientePorCnpj(mensagemRequest.getCnpjCliente());
        validaSaldoDisponivel(cliente, 1);
        atualizarSaldoOuLimite(cliente, 1);
        MensagemSender mensagemSender = verificaTipoMensagem(mensagemRequest.getTipoMensagem());
        mensagemSender.enviar(cliente.getTelefone(), mensagemRequest.getTelefone(), mensagemRequest.getMensagem());
    }

    public void processarMensagensEmLote(MensagemBatchRequest mensagemBatchRequest) {
        ClienteResponse cliente = clienteService.buscarClientePorCnpj(mensagemBatchRequest.getCnpjCliente());

        int quantidadeMensagens = mensagemBatchRequest.getTelefones().size();
        validaSaldoDisponivel(cliente, quantidadeMensagens);
        atualizarSaldoOuLimite(cliente, quantidadeMensagens);

        MensagemSender mensagemSender = verificaTipoMensagem(mensagemBatchRequest.getTipoMensagem());
        mensagemBatchRequest.getTelefones().forEach(telefone ->
            mensagemSender.enviar(cliente.getTelefone(), telefone, mensagemBatchRequest.getMensagem()));
    }

    private MensagemSender verificaTipoMensagem(TipoMensagemEnum tipoMensagem) {
        return mensagemSenders.stream()
                .filter(sender -> sender.getTipoMensagem().equals(tipoMensagem))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Tipo de mensagem não suportado."));
    }

    private void validaSaldoDisponivel(ClienteResponse cliente, int quantidadeMensagens) {
        if (cliente.getNomePlano().equals(TipoPlanoEnum.PRE_PAGO.getDescricao())) {
            double saldoNecessario = quantidadeMensagens * PRECO_POR_MENSAGEM_PRE_PAGO;
            if (cliente.getSaldo() < saldoNecessario) {
                throw new BusinessException("Saldo insuficiente.");
            }
        } else {
            double limiteNecessario = quantidadeMensagens * CREDITO_POR_MENSAGEM_POS_PAGO;
            if (cliente.getLimiteCredito() < limiteNecessario) {
                throw new BusinessException("Limite de crédito excedido.");
            }
        }
    }

    private void atualizarSaldoOuLimite(ClienteResponse cliente, int quantidadeMensagens) {
        double saldo;
        if (cliente.getNomePlano().equals(TipoPlanoEnum.PRE_PAGO.getDescricao())) {
            saldo = (cliente.getSaldo() - (quantidadeMensagens * PRECO_POR_MENSAGEM_PRE_PAGO));
        } else {
            saldo = (cliente.getLimiteCredito() - (quantidadeMensagens * CREDITO_POR_MENSAGEM_POS_PAGO));
        }
        clienteService.atualizarSaldo(cliente.getNomePlano(),  cliente.getCnpj(),  saldo);
    }
}
