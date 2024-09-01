package com.BankStatementAggregator.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.BranchDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Branch;
import com.BankStatementAggregator.errorhandiling.BranchServiceExceptionHandler;
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
		Branch branch = mapper.map(branchDTO, Branch.class);
		
		Bank branchByBankCode = bankRepository.findByBankCode(branchDTO.getBankCode() );
		if(branchByBankCode==null) {
			throw new BranchServiceExceptionHandler("Bank Code is invalid", HttpStatus.NOT_FOUND);
		}
		if(branchByBankCode.getBranches().contains(branchDTO.getBankCode())) {
			throw new BranchServiceExceptionHandler("Branch in that bank is present", HttpStatus.BAD_REQUEST);
		}
		branch.setBank(branchByBankCode);
		Branch saveBranch = branchRepository.save(branch);
		if(saveBranch==null) {
			throw new BranchServiceExceptionHandler("Unable to create Branch! Unknown error occure.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED)
			.body(new ApiResponse(HttpStatus.CREATED.value(),"Branch is created",null));
		}
		
	}
	
	
	public ResponseEntity<?> getBranchById(Long id) {
		Optional<Branch> branchById = branchRepository.findById(id);
		if(branchById.isEmpty()) {
			throw new BranchServiceExceptionHandler("Branch with Id: "+id+" is not available", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new ApiResponse(HttpStatus.ACCEPTED.value(), "Branch with Id: "+id+" is not available", branchById.get()));
	}
	
	
	public ResponseEntity<List<Branch>> getAllBranches() {
		List<Branch> all = branchRepository.findAll();
		if(all==null) {
			throw new BranchServiceExceptionHandler("No Branch Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Branch>>(all,HttpStatus.OK);
	
	}
	public ResponseEntity<?> updateBranch(Long id, BranchDTO branchDTO) {
		Optional<Branch> branchById = branchRepository.findById(id);
		if(branchById.isEmpty()) {
			throw new BranchServiceExceptionHandler("Branch with ID: "+id+" is not available for update.", HttpStatus.BAD_REQUEST);
		}
		
		Optional<Bank> bankByCode = Optional.ofNullable(bankRepository.findByBankCode(branchDTO.getBankCode()));
		if(bankByCode.isEmpty()) {
			throw new BranchServiceExceptionHandler("Bank Code is invalid", HttpStatus.BAD_REQUEST);
		}
		
		Branch branch = branchById.get();
		branch.setBranchName(branchDTO.getBranchName());
		branch.setBranchAddress(branchDTO.getBranchAddress());
		branch.setBank(bankByCode.get());
		
		
			Branch updatedBranch = branchRepository.save(branch);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(HttpStatus.OK.value(), "Branch Updated!", updatedBranch));
	}
	
	
	
	public ResponseEntity<Branch> removeById(Long id) {
		Optional<Branch> branchById = branchRepository.findById(id);
		if(branchById.isEmpty()) {
			throw new BranchServiceExceptionHandler("Branch with ID: "+id+" is not available for delete.", HttpStatus.BAD_REQUEST);
		}
		branchRepository.deleteById(id);
		return new ResponseEntity("Deleted Branch by ID: "+id ,HttpStatus.OK);
	}

}
