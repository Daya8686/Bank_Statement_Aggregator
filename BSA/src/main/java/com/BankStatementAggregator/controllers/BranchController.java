package com.BankStatementAggregator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.BranchDTO;
import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.Enitiy.Branch;
import com.BankStatementAggregator.Enitiy.Company;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBranchById(@PathVariable Long id){
		return branchService.getBranchById(id);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Branch>> getAllBranches(){
		return branchService.getAllBranches();
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBranchById(@PathVariable Long id, @RequestBody BranchDTO branchDTO){
		return branchService.updateBranch(id, branchDTO);
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Branch> deleteBranchById(@PathVariable Long id){
		return branchService.getRemoveById(id);
	}

}
