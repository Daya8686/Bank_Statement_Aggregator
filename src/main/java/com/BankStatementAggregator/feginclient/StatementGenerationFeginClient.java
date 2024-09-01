package com.BankStatementAggregator.feginclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.BankStatementAggregator.DTOs.BankStatementRecevierDTO;
import com.BankStatementAggregator.DTOs.StatementGenerationDTO;

@FeignClient(name="statementgeneration")
public interface StatementGenerationFeginClient  {
	
	@PostMapping("/generate")
	public ResponseEntity<?> BankStatementsGenerator( StatementGenerationDTO bankStatementDTO);

}
