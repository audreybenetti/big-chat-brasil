package br.com.bcb.repository;

import br.com.bcb.api.cliente.TipoPlanoEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCnpj(String cnpj);
    Optional<Cliente> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.saldo = :saldo WHERE c.cnpj = :cnpj")
    void updateSaldoByCnpj(Double saldo, String cnpj);

    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.limiteCredito = :limiteCredito WHERE c.cnpj = :cnpj")
    void updateCreditoByCnpj(Double limiteCredito, String cnpj);

    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.plano = :plano WHERE c.cnpj = :cnpj")
    void updatePlanoByCnpj(TipoPlanoEnum plano, String cnpj);
}
