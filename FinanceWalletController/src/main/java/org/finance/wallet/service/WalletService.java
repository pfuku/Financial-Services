package org.finance.wallet.service;

import org.finance.infra.database.entity.WalletEntity;
import org.finance.infra.database.entity.WalletTransactionEntity;
import org.finance.infra.database.repository.WalletRepository;
import org.finance.infra.database.repository.WalletTransactionRepository;
import org.finance.wallet.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        // Check that only one wallet can be opened per currency
        if (walletRepository.existsByUserIdAndCurrencyCodeAndState(
                request.getUserId(), request.getCurrencyCode(), 1)) {
            throw new IllegalArgumentException(
                    "A wallet already exists for this currency: " + request.getCurrencyCode());
        }

        WalletEntity wallet = new WalletEntity();
        wallet.setUserId(request.getUserId());
        wallet.setCurrencyCode(request.getCurrencyCode().toUpperCase());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setStatus(1);
        wallet.setState(1);

        WalletEntity savedWallet = walletRepository.save(wallet);
        return mapToWalletResponse(savedWallet);
    }

    public WalletListResponse getUserWallets(Long userId) {
        List<WalletEntity> wallets = walletRepository.findByUserIdAndState(userId, 1);
        List<WalletResponse> walletResponses = wallets.stream()
                .map(this::mapToWalletResponse)
                .collect(Collectors.toList());

        WalletListResponse response = new WalletListResponse();
        response.setUserId(userId);
        response.setWallets(walletResponses);
        return response;
    }

    public WalletResponse getWallet(Long walletId) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found: " + walletId));
        return mapToWalletResponse(wallet);
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        // Check source wallet
        WalletEntity fromWallet = walletRepository.findById(request.getFromWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found: " + request.getFromWalletId()));

        // Check destination wallet
        WalletEntity toWallet = walletRepository.findById(request.getToWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Destination wallet not found: " + request.getToWalletId()));

        // User validation - wallets must belong to the same user
        Long fromUserId = fromWallet.getUserId();
        Long toUserId = toWallet.getUserId();
        Long requestUserId = request.getUserId();
        
        if (fromUserId == null || toUserId == null || requestUserId == null ||
            !fromUserId.equals(requestUserId) || !toUserId.equals(requestUserId)) {
            throw new IllegalArgumentException("Wallets must belong to the same user");
        }

        // Active wallet validation
        if (fromWallet.getState() != 1 || toWallet.getState() != 1) {
            throw new IllegalArgumentException("Wallets must be active");
        }

        // Balance validation
        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance. Current balance: " + fromWallet.getBalance());
        }

        // Negative amount validation
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }

        // Same wallet transfer validation
        if (fromWallet.getId().equals(toWallet.getId())) {
            throw new IllegalArgumentException("Cannot transfer to the same wallet");
        }

        // Currency validation - both wallets must have the same currency
        if (!fromWallet.getCurrencyCode().equals(toWallet.getCurrencyCode())) {
            throw new IllegalArgumentException(
                    "Cannot transfer between different currencies. Source currency: " + 
                    fromWallet.getCurrencyCode() + ", Destination currency: " + toWallet.getCurrencyCode());
        }

        // Perform transfer
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // Create transaction record
        WalletTransactionEntity transaction = new WalletTransactionEntity();
        transaction.setFromWalletId(fromWallet.getId());
        transaction.setToWalletId(toWallet.getId());
        transaction.setUserId(request.getUserId());
        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(request.getAmount());
        transaction.setCurrencyCode(fromWallet.getCurrencyCode()); // Both wallets have the same currency
        transaction.setDescription(request.getDescription());
        transaction.setStatus(1);

        WalletTransactionEntity savedTransaction = walletTransactionRepository.save(transaction);
        return mapToTransactionResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionHistory(Long userId) {
        List<WalletTransactionEntity> transactions = walletTransactionRepository
                .findByUserIdOrderByCreateDateDesc(userId);
        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getWalletTransactionHistory(Long walletId) {
        List<WalletTransactionEntity> transactions = walletTransactionRepository
                .findByFromWalletIdOrToWalletIdOrderByCreateDateDesc(walletId, walletId);
        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    private WalletResponse mapToWalletResponse(WalletEntity wallet) {
        WalletResponse response = new WalletResponse();
        response.setId(wallet.getId());
        response.setUserId(wallet.getUserId());
        response.setCurrencyCode(wallet.getCurrencyCode());
        response.setBalance(wallet.getBalance());
        response.setCreateDate(wallet.getCreateDate());
        response.setUpdateDate(wallet.getUpdateDate());
        response.setStatus(wallet.getStatus());
        response.setState(wallet.getState());
        return response;
    }

    private TransactionResponse mapToTransactionResponse(WalletTransactionEntity transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setFromWalletId(transaction.getFromWalletId());
        response.setToWalletId(transaction.getToWalletId());
        response.setUserId(transaction.getUserId());
        response.setTransactionType(transaction.getTransactionType());
        response.setAmount(transaction.getAmount());
        response.setCurrencyCode(transaction.getCurrencyCode());
        response.setDescription(transaction.getDescription());
        response.setCreateDate(transaction.getCreateDate());
        response.setStatus(transaction.getStatus());
        return response;
    }
}

