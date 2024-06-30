package com.BankStatementAggregator.DTOs;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BankStatementRecevierDTO {
	
	
	@Min(value = 1000, message = "Company Code must be at least 4 digits")
    @Max(value = 999999, message = "Compay Code must be at most 6 digits")
	@Column(nullable = false)
	private int companyCode;
	
	@Min(value = 1000, message = "Bank Code must be at least 4 digits")
    @Max(value = 999999, message = "Bank Code must be at most 6 digits")
	@Column(nullable = false)
	private int bankCode;
	
	
	@Column(nullable = false)
	private Long branchId;
	
	@Column(nullable = false)
	private LocalDate statementDate;
	
	
	@Column(nullable = false)
	private String statementCode;
	
	@NotBlank
	@Column(nullable = false)
	private String companyName;

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public int getBankCode() {
		return bankCode;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public LocalDate getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(LocalDate statementDate) {
		this.statementDate = statementDate;
	}

	public String getStatementCode() {
		return statementCode;
	}

	public void setStatementCode(String statementCode) {
		this.statementCode = statementCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

	

}
