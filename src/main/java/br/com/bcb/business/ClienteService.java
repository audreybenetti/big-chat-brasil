package br.com.bcb.business;

import br.com.bcb.api.ClienteRequest;
import br.com.bcb.api.ClienteResponse;
import br.com.bcb.api.TipoPlanoEnum;
import br.com.bcb.repository.ClienteEntity;
import br.com.bcb.repository.ClienteRepository;
import br.com.bcb.config.exceptions.NoRecordsFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteResponse salvarCliente(ClienteRequest clienteRequest) {
        ClienteEntity entity = ClienteEntity.of(clienteRequest);
        clienteRepository.save(entity);
        return ClienteResponse.of(entity);
    }

    public ClienteResponse buscarClientePorCnpj(String cpnj) {
        ClienteEntity entity = clienteRepository.findByCnpj(cpnj)
                .orElseThrow(() -> new NoRecordsFoundException("Não foi encontrada empresa com este CNPJ."));
        return ClienteResponse.of(entity);
    }

    public Double consultarSaldo(String cnpj) {
        ClienteResponse cliente = buscarClientePorCnpj(cnpj);
        if (cliente.getNomePlano().equals(TipoPlanoEnum.PRE_PAGO.getDescricao())) {
            return cliente.getSaldo();
        } else {
            return cliente.getLimiteCredito();
        }
    }

    public void atualizarSaldo(BigDecimal novoSaldo, String cpnj) {
        buscarClientePorCnpj(String.valueOf(cpnj));
        clienteRepository.updateSaldoByCnpj(novoSaldo, cpnj);
    }

    public void atualizarPlano(String plano, String cpnj) {
        buscarClientePorCnpj(String.valueOf(cpnj));
        clienteRepository.updatePlanoByCnpj(plano, cpnj);
    }
}
