package org.finance.infra.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "FNC_WALLET_TRANSACTION", schema = "FNC")
@Getter
@Setter
public class WalletTransactionEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FROM_WALLET_ID")
    private Long fromWalletId;

    @Column(name = "TO_WALLET_ID")
    private Long toWalletId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "TRANSACTION_TYPE", length = 20, nullable = false)
    private String transactionType; // TRANSFER, DEPOSIT, WITHDRAWAL

    @Column(name = "AMOUNT", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY_CODE", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "CREATEDATE", nullable = false)
    private Long createDate;

    @Column(name = "STATUS")
    private Integer status;

    @PrePersist
    protected void onCreate() {
        if (createDate == null) {
            createDate = System.currentTimeMillis();
        }
        if (status == null) {
            status = 1; // Completed
        }
    }
}

