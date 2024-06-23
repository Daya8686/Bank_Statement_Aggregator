package com.BankStatementAggregator.DTOs;

import java.util.Set;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;

@ToString
public class UserDTO {


	@NotNull(message = "User name cannot be null")
	@Size(min = 1, max = 45, message = "User name must be between 1 and 45 characters")
	private String userName;

	@NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
	private String userPassword;

	@Transient
	@NotNull(message = "Confirm Password cannot be null")
	private String cPassword;

	@NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
	private String userEmail;

	@Min(value = 1000, message = "Company Code must be at least 4 digits")
    @Max(value = 999999, message = "Company Code must be at most 6 digits")
	private Integer companyCode;
	
	@NotNull(message = "Bank Codes cannot be null")
    @Size(min = 1, message = "At least one Bank Code must be provided")
	private Set<Integer> bankCodes;

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
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
