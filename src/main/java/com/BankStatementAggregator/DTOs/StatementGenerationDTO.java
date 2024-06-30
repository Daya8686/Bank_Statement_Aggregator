package com.BankStatementAggregator.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StatementGenerationDTO {
	
	@NotBlank
	@Min(value = 1000, message = "Company Code must be at least 4 digits")
	@Max(value = 999999, message = "Company Code must be at most 6 digits")
	private Integer companyCode;
	
	@NotBlank
	@Min(value = 1000, message = "Bank Code must be at least 4 digits")
    @Max(value = 999999, message = "Bank Code must be at most 6 digits")
	private Integer BankCode;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branch_id")
    private Long branchId;
	
	@NotBlank
	private int numberOfTransactionsGenerate;
	
	@NotBlank
	private boolean deleteAfterUpload;
	
	@NotBlank
	private String CompanyName;

	public Integer getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getBankCode() {
		return BankCode;
	}

	public void setBankCode(Integer bankCode) {
		BankCode = bankCode;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public int getNumberOfTransactionsGenerate() {
		return numberOfTransactionsGenerate;
	}

	public void setNumberOfTransactionsGenerate(int numberOfTransactionsGenerate) {
		this.numberOfTransactionsGenerate = numberOfTransactionsGenerate;
	}

	public boolean isDeleteAfterUpload() {
		return deleteAfterUpload;
	}

	public void setDeleteAfterUpload(boolean deleteAfterUpload) {
		this.deleteAfterUpload = deleteAfterUpload;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	

	
	
	

}
