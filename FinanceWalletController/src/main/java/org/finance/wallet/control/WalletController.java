package org.finance.wallet.control;

import org.finance.wallet.model.*;
import org.finance.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<WalletResponse> createWallet(@RequestBody CreateWalletRequest request) {
        try {
            WalletResponse response = walletService.createWallet(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<WalletListResponse> getUserWallets(@RequestBody GetUserWalletsRequest request) {
        WalletListResponse response = walletService.getUserWallets(request.getUserId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get")
    public ResponseEntity<WalletResponse> getWallet(@RequestBody GetWalletRequest request) {
        try {
            WalletResponse response = walletService.getWallet(request.getWalletId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {
        try {
            TransactionResponse response = walletService.transfer(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/transactions/user")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@RequestBody GetTransactionHistoryRequest request) {
        List<TransactionResponse> transactions = walletService.getTransactionHistory(request.getUserId());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions/wallet")
    public ResponseEntity<List<TransactionResponse>> getWalletTransactionHistory(@RequestBody GetWalletTransactionHistoryRequest request) {
        List<TransactionResponse> transactions = walletService.getWalletTransactionHistory(request.getWalletId());
        return ResponseEntity.ok(transactions);
    }
}

