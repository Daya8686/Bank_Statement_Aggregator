package com.BankStatementAggregator.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.BranchDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Branch;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.BranchRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class BranchService {
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public ResponseEntity<?> createBranch(BranchDTO branchDTO) {
		try {
		Branch branch = mapper.map(branchDTO, Branch.class);
		
		Bank bankByBankCode = bankRepository.findByBankCode(branchDTO.getBankCode() );
		if(bankByBankCode==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiResponse(HttpStatus.NOT_FOUND.value(),"Bank Code is invalid",branchDTO));
		}
		branch.setBank(bankByBankCode);
		Branch saveBranch = branchRepository.save(branch);
		if(saveBranch==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Unable to create Branch! Unknown error occure.",branchDTO));
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED)
			.body(new ApiResponse(HttpStatus.CREATED.value(),"Branch is created",null));
		}
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Branch is not created",e.getLocalizedMessage()));
		}
		
	}

}
