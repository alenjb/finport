package com.example.finport.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByContract(Long contractId) {
        return transactionRepository.findAll().stream()
                .filter(t -> t.getContract().getId().equals(contractId))
                .collect(Collectors.toList());
    }
}