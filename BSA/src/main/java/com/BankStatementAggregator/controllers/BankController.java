package com.BankStatementAggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.BankDTO;
import com.BankStatementAggregator.services.BankServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Bank")
public class BankController {

	@Autowired
	private BankServices bankServices;

	@PostMapping("/create")
	public ResponseEntity<?> bankCreation(@Valid @RequestBody BankDTO bankDTO) {

		return bankServices.bankCreation(bankDTO);

	}

}
