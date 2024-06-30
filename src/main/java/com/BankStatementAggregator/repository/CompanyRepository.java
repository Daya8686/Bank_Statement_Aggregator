package com.BankStatementAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankStatementAggregator.Enitiy.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Company findByCompanyCode(Integer companyCode);

}
