package br.com.bcb.business;

import br.com.bcb.api.cliente.ClienteRequest;
import br.com.bcb.api.cliente.ClienteResponse;
import br.com.bcb.api.cliente.TipoPlanoEnum;
import br.com.bcb.repository.Cliente;
import br.com.bcb.repository.ClienteRepository;
import br.com.bcb.config.exceptions.NoRecordsFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteResponse salvarCliente(ClienteRequest clienteRequest) {
        Cliente entity = Cliente.of(clienteRequest);
        clienteRepository.save(entity);
        return ClienteResponse.of(entity);
    }

    public ClienteResponse buscarClientePorCnpj(String cnpj) {
        Cliente entity = clienteRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NoRecordsFoundException("Não foi encontrada empresa com este cnpj: " + cnpj));
        return ClienteResponse.of(entity);
    }

    public ClienteResponse buscarClientePorEmail(String email) {
        Cliente entity = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NoRecordsFoundException("Não foi encontrada empresa com este e-mail: " + email));
        return ClienteResponse.of(entity);
    }

    public List<ClienteResponse> buscarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteResponse::of).toList();
    }

    public Double consultarSaldo(String email) {
        ClienteResponse cliente = buscarClientePorEmail(email);
        if (cliente.getNomePlano().equals(TipoPlanoEnum.PRE_PAGO.getDescricao())) {
            return cliente.getSaldo();
        } else {
            return cliente.getLimiteCredito();
        }
    }

    public void atualizarSaldo(String plano, String cnpj, Double novoSaldo) {
        if (plano.equals(TipoPlanoEnum.PRE_PAGO.getDescricao())) {
            clienteRepository.updateSaldoByCnpj(novoSaldo, cnpj);
        } else {
            clienteRepository.updateCreditoByCnpj(novoSaldo, cnpj);
        }
    }

    public void atualizarDadosPlano(String plano, Double saldo, String cpnj) {
        ClienteResponse cliente = buscarClientePorCnpj(cpnj);
        TipoPlanoEnum planoEnum = plano.equals(TipoPlanoEnum.PRE_PAGO.getDescricao()) ? TipoPlanoEnum.PRE_PAGO : TipoPlanoEnum.POS_PAGO;
        atualizarSaldo(cliente.getNomePlano(), cpnj, saldo);
        clienteRepository.updatePlanoByCnpj(planoEnum, cliente.getCnpj());
    }
}
