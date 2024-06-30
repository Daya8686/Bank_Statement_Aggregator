package com.BankStatementAggregator.Enitiy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@ToString
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private long userId;
	
	@Column(name="user_name", nullable = false, unique = true)
	private String userName;
	

	@Column(name="user_password")
	private String userPassword;
	
	
	@Column(name="user_email",nullable = false, unique = true)
	private String userEmail;
	
	@Column(name="user_register_date")
	private LocalDate userRegistorDate;
	
	@Column(name="user_iselgible")
	private boolean userIsElgible;
	
	@Column(name="user_elgible_date")
	private LocalDate userElgibleDate;
	
	@Column(name="user_last_password_change_date")
	private LocalDateTime userLastPasswordChangeDate;
	
	@Column(name="user_last_login")
	private LocalDateTime userLastLogin;
	
	@Column(name="user_invalid_attempts")
	private Integer userInvalidAttempts;
	
	@ManyToOne
	@JoinColumn(name="company_id", referencedColumnName = "company_id")
	private Company company;
	
//	@ManyToMany
//    @JoinTable(
//        name = "user_bank",
//        joinColumns = @JoinColumn(name = "user_id"),
//        inverseJoinColumns = @JoinColumn(name = "bank_id"))
//    private Set<Bank> banks; // Many users can have many banks
	
	
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	

//	public Set<Bank> getBanks() {
//		return banks;
//	}
//
//	public void setBanks(Set<Bank> banks) {
//		this.banks = banks;
//	}

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
	

	
	

}
