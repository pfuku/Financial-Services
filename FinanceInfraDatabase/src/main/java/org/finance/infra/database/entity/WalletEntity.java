package org.finance.infra.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "FNC_WALLET", schema = "FNC")
@Getter
@Setter
public class WalletEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CURRENCY_CODE", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "BALANCE", precision = 19, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "CREATEDATE")
    private Long createDate;

    @Column(name = "UPDATEDATE")
    private Long updateDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "STATE")
    private Integer state;

    @PrePersist
    protected void onCreate() {
        if (createDate == null) {
            createDate = System.currentTimeMillis();
        }
        if (status == null) {
            status = 1; // Active
        }
        if (state == null) {
            state = 1; // Active
        }
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = System.currentTimeMillis();
    }
}

