package com.BankStatementAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

	void deleteAllByBank(Bank bank);

}
