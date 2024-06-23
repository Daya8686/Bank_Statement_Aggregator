package com.BankStatementAggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.services.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> CompanyAdding(@Valid @RequestBody CompanyDTO companyDTO){
		return companyService.creatingCompany(companyDTO);
		
	}

}
