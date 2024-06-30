package com.BankStatementAggregator.DTOs;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserDTO {
	
	
	
	@NotNull(message = "User name cannot be null")
	@Size(min = 1, max = 45, message = "User name must be between 1 and 45 characters")
	private String userName;
	
	@NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
	private String userEmail;

	@Min(value = 1000, message = "Company Code must be at least 4 digits")
    @Max(value = 999999, message = "Company Code must be at most 6 digits")
	private Integer companyCode;
	
	@NotNull(message = "Bank Codes cannot be null")
    @Size(min = 1, message = "At least one Bank Code must be provided")
	private Set<Integer> bankCodes;

	public UpdateUserDTO(
			@NotNull(message = "User name cannot be null") @Size(min = 1, max = 45, message = "User name must be between 1 and 45 characters") String userName,
			@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String userEmail,
			@Min(value = 1000, message = "Company Code must be at least 4 digits") @Max(value = 999999, message = "Company Code must be at most 6 digits") Integer companyCode,
			@NotNull(message = "Bank Codes cannot be null") @Size(min = 1, message = "At least one Bank Code must be provided") Set<Integer> bankCodes) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.companyCode = companyCode;
		this.bankCodes = bankCodes;
	}

	public UpdateUserDTO() {
		super();
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public Set<Integer> getBankCodes() {
		return bankCodes;
	}

	public void setBankCodes(Set<Integer> bankCodes) {
		this.bankCodes = bankCodes;
	}


	

}
