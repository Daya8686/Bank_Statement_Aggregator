package com.BankStatementAggregator.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BranchDTO {

	@NotBlank(message = "Branch Name cannot be blank or empty")
	@Size(min = 5, max = 200, message = "Branch name must be min 5 and max 200 charaters")
	private String branchName;
	
	@NotBlank(message = "Bank address cannot be blank or empty")
	@Size(min = 5, max = 200, message = "Bank address must be min 5 and max 200 charaters")
    private String branchAddress;
	
	@NotNull(message = "Bank code must not be blank")
	@Size(min=4, max=6, message = "Bank Code must be min 4 and max 6 Numbers")
	private int bankCode;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public int getBankCode() {
		return bankCode;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	

	
	
}
