package com.BankStatementAggregator.DTOs;

import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;

@ToString
public class CompanyDTO {

	@NotBlank(message = "Company Name cannot be blank or empty")
	@Size(min = 2, max = 200, message = "Company name must be min 2 and max 200 charaters")
	private String companyName;

	@Min(value = 1000, message = "Company Code must be at least 4 digits")
	@Max(value = 999999, message = "Company Code must be at most 6 digits")
	private int companyCode;

	@NotNull(message = "Bank Codes cannot be null")
	@Size(min = 1, message = "At least one Bank Code must be provided")
	private Set<Integer> bankCodes;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public Set<Integer> getBankCodes() {
		return bankCodes;
	}

	public void setBankCodes(Set<Integer> bankCodes) {
		this.bankCodes = bankCodes;
	}

}
