package com.example.finport.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> getContractsByUser(Long userId) {
        return contractRepository.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }
}