package com.BankStatementAggregator.DTOs;

import jakarta.persistence.Column;

public class BankDTOWithoutBranch {
	
	@Column(name="bank_name", unique = true)
    private String bankName;
	@Column(name="bank_code", unique = true)
    private int bankCode;
	

	public BankDTOWithoutBranch() {
		super();
	}
	
	public BankDTOWithoutBranch(String bankName, int bankCode) {
		super();
		this.bankName = bankName;
		this.bankCode = bankCode;
	}

	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getBankCode() {
		return bankCode;
	}
	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}


	

}
