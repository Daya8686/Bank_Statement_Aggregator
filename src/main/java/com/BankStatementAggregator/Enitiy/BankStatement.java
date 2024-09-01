package com.BankStatementAggregator.Enitiy;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="bankstatement")
@ToString
public class BankStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="statement_id")
    private Long statementId;

    @Column(name="statement_date")
    private LocalDate statementDate;
    
    @Column(name="statement_code")
    private String statementCode;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonIgnore
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    @JsonIgnore
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    @JsonIgnore
    private Branch branch;



	public Long getStatementId() {
		return statementId;
	}

	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}

	public LocalDate getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(LocalDate statementDate) {
		this.statementDate = statementDate;
	}

	
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	

	public Bank getBank() {
		return bank;
	}

	public String getStatementCode() {
		return statementCode;
	}

	public void setStatementCode(String statementCode) {
		this.statementCode = statementCode;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

    
}


