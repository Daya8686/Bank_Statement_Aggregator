package com.BankStatementAggregator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.StatementGenerationDTO;
import com.BankStatementAggregator.services.BankStatementService;

@RestController
@RequestMapping("/statement")
public class BankStatementController {

	@Autowired
	private BankStatementService bankStatementService;

	@PostMapping("/generate")
	public ResponseEntity<?> generateStatement(@RequestBody StatementGenerationDTO generationDTO) {
		return bankStatementService.statementGenerator(generationDTO);

	}
	
	@PostMapping("/generate/allBanks/company/{companyCode}")
	public ResponseEntity<List<?>> generateStatementByCompany(@PathVariable Integer companyCode){
		return bankStatementService.statementGenerateByCompany(companyCode);
	}

}
