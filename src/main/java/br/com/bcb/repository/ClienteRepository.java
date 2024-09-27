package br.com.bcb.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCnpj(String cnpj);

    @Modifying
    @Transactional
    @Query("UPDATE ClienteEntity c SET c.saldo = :saldo WHERE c.cnpj = :cnpj")
    void updateSaldoByCnpj(BigDecimal saldo, String cnpj);

    @Modifying
    @Transactional
    @Query("UPDATE ClienteEntity c SET c.plano = :plano WHERE c.cnpj = :cnpj")
    void updatePlanoByCnpj(String plano, String cnpj);
}
