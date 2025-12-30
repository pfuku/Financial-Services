package org.finance.infra.database.repository;

import org.finance.infra.database.entity.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Long> {
    
    List<WalletTransactionEntity> findByUserIdOrderByCreateDateDesc(Long userId);
    
    List<WalletTransactionEntity> findByFromWalletIdOrToWalletIdOrderByCreateDateDesc(Long fromWalletId, Long toWalletId);
}

