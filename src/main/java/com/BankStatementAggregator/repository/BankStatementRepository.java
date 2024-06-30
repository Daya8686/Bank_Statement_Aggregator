package com.BankStatementAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankStatementAggregator.Enitiy.BankStatement;

@Repository
public interface BankStatementRepository extends JpaRepository<BankStatement, Integer> {

}
