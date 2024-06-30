package com.BankStatementAggregator.Enitiy;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;


@Entity
@Table(name="branch")
@ToString
public class Branch {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branch_id")
    private Long branchId;

	@Column(name="branch_name")
    private String branchName;
	@Column(name="branch_address")
    private String branchAddress;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id",nullable = false)
    @JsonIgnore
    private Bank bank;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<BankStatement> bankStatements;
    

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Set<BankStatement> getBankStatements() {
		return bankStatements;
	}

	public void setBankStatements(Set<BankStatement> bankStatements) {
		this.bankStatements = bankStatements;
	}

	
    
    
}
