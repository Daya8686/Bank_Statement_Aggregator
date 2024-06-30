package com.BankStatementAggregator.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BankDTO {
	
	@NotBlank(message = "Bank Name cannot be blank or empty")
	@Size(min = 5, max = 200, message = "Bank name must be min 5 and max 200 charaters")
    private String bankName;
	
	
	@Min(value = 1000, message = "Bank Code must be at least 4 digits")
    @Max(value = 999999, message = "Bank Code must be at most 6 digits")
    private int bankCode;

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
