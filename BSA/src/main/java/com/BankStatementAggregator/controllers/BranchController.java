package com.BankStatementAggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.BranchDTO;
import com.BankStatementAggregator.services.BranchService;

@RestController
@RequestMapping("/Branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@PostMapping("/create")
	public ResponseEntity<?> addingBranch(@RequestBody BranchDTO branchDTO){
		return branchService.createBranch(branchDTO);
		
	}

}
