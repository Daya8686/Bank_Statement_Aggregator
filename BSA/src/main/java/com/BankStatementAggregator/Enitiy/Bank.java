package com.BankStatementAggregator.Enitiy;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="bank")
@ToString
public class Bank {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bank_id")
    private Long bankId;

	@Column(name="bank_name", unique = true)
    private String bankName;
	@Column(name="bank_code", unique = true)
    private int bankCode;

//	@ManyToMany(mappedBy = "banks", cascade = CascadeType.ALL)
//	@JsonIgnore
//    private Set<User> users; // Many banks can have many users
	
//	@ManyToMany(mappedBy = "banks", cascade = CascadeType.ALL)
//    private Set<Company> company; // Many banks can have many users


    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private Set<Branch> branches;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
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

//	public Set<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}

	public Set<Branch> getBranches() {
		return branches;
	}

//	public Set<Company> getCompany() {
//		return company;
//	}
//
//	public void setCompany(Set<Company> company) {
//		this.company = company;
//	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}

    
}
