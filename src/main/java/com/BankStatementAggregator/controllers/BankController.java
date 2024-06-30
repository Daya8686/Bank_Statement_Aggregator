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

import com.BankStatementAggregator.DTOs.BankDTO;
import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
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

	@GetMapping("/{id}")
	public ResponseEntity<?> getBankById(@PathVariable Long id) {
		return bankServices.getBankById(id);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Bank>> getAllBank() {
		return bankServices.getAllBank();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBankById(@PathVariable Long id, @RequestBody BankDTO bankDTO) {
		return bankServices.updateBank(id, bankDTO);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Bank> deleteBankById(@PathVariable Long id) {
		return bankServices.getRemoveById(id);
	}

}
