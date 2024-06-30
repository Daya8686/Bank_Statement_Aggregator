package com.BankStatementAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankStatementAggregator.Enitiy.Bank;

public interface BankRepository extends JpaRepository<Bank, Long>{

		Bank findByBankCode(int bankCode);

}
