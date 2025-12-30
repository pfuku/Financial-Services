package org.finance.infra.database.repository;

import org.finance.infra.database.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    
    Optional<WalletEntity> findByUserIdAndCurrencyCodeAndState(Long userId, String currencyCode, Integer state);
    
    List<WalletEntity> findByUserIdAndState(Long userId, Integer state);
    
    boolean existsByUserIdAndCurrencyCodeAndState(Long userId, String currencyCode, Integer state);
}

