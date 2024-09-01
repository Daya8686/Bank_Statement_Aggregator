package com.BankStatementAggregator.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UsersWithCompanyId")
public class UserToUserDTO {

	private long userId;

	private String userName;

	private String userEmail;

	private LocalDate userRegistorDate;

	private boolean userIsElgible;

	private LocalDate userElgibleDate;

	private LocalDateTime userLastPasswordChangeDate;

	private LocalDateTime userLastLogin;

	private Integer userInvalidAttempts;

	private CompanyToCompanyDTO company;



	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public LocalDate getUserRegistorDate() {
		return userRegistorDate;
	}

	public void setUserRegistorDate(LocalDate userRegistorDate) {
		this.userRegistorDate = userRegistorDate;
	}

	public boolean isUserIsElgible() {
		return userIsElgible;
	}

	public void setUserIsElgible(boolean userIsElgible) {
		this.userIsElgible = userIsElgible;
	}

	public LocalDate getUserElgibleDate() {
		return userElgibleDate;
	}

	public void setUserElgibleDate(LocalDate userElgibleDate) {
		this.userElgibleDate = userElgibleDate;
	}

	public LocalDateTime getUserLastPasswordChangeDate() {
		return userLastPasswordChangeDate;
	}

	public void setUserLastPasswordChangeDate(LocalDateTime userLastPasswordChangeDate) {
		this.userLastPasswordChangeDate = userLastPasswordChangeDate;
	}

	public LocalDateTime getUserLastLogin() {
		return userLastLogin;
	}

	public void setUserLastLogin(LocalDateTime userLastLogin) {
		this.userLastLogin = userLastLogin;
	}

	public Integer getUserInvalidAttempts() {
		return userInvalidAttempts;
	}

	public void setUserInvalidAttempts(Integer userInvalidAttempts) {
		this.userInvalidAttempts = userInvalidAttempts;
	}

	public CompanyToCompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyToCompanyDTO company) {
		this.company = company;
	}

//	public Set<Bank> getBanks() {
//		return banks;
//	}
//
//	public void setBanks(Set<Bank> banks) {
//		this.banks = banks;
//	}
//	

}
