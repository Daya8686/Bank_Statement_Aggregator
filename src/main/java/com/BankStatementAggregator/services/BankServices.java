package com.BankStatementAggregator.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.BankDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.errorhandiling.BankServiceExceptionHandler;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.BranchRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class BankServices {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<?> bankCreation(BankDTO bankDTO) {
		Bank bank = mapper.map(bankDTO, Bank.class);
		if (bankRepository.findByBankCode(bank.getBankCode()) != null) {
			throw new BankServiceExceptionHandler("Bank is already avaliable with that bank code",
					HttpStatus.BAD_REQUEST);
		}
		Bank saveBank = bankRepository.save(bank);
		if (saveBank == null) {
			throw new BankServiceExceptionHandler("Unable to create Bank due to unknown error",
					HttpStatus.INTERNAL_SERVER_ERROR);

		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse(HttpStatus.CREATED.value(), "Bank Creation Success", saveBank));
		}

	}

	public ResponseEntity<?> getBankById(Long id) {
		Optional<Bank> bankById = bankRepository.findById(id);
		if (bankById.isEmpty()) {
			throw new BankServiceExceptionHandler("Bank with Id: " + id + " is not available", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(HttpStatus.ACCEPTED.value(),
				"Bank with Id: " + id + " is not available", bankById.get()));
	}

	public ResponseEntity<List<Bank>> getAllBank() {
		List<Bank> all = bankRepository.findAll();
		if (all == null) {
			throw new BankServiceExceptionHandler("No Bank Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Bank>>(all, HttpStatus.OK);
	}

	public ResponseEntity<?> updateBank(Long id, BankDTO bankDTO) {
		Optional<Bank> bankById = bankRepository.findById(id);
		if (bankById.isEmpty()) {
			throw new BankServiceExceptionHandler("Bank with ID: " + id + " is not available for update.",
					HttpStatus.BAD_REQUEST);
		}

		Bank bank = bankById.get();
		bank.setBankCode(bankDTO.getBankCode());
		bank.setBankName(bankDTO.getBankName());

		Bank updatedBank = bankRepository.save(bank);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Bank Updated!", updatedBank));
	}

	public ResponseEntity<Bank> getRemoveById(Long id) {
		Optional<Bank> bankById = bankRepository.findById(id);
		if (bankById.isEmpty()) {
			throw new BankServiceExceptionHandler("Bank with ID: " + id + " is not available for delete.",
					HttpStatus.BAD_REQUEST);
		}
		bankRepository.deleteById(id);
		// By This all branches which are associated with bank are deleted.
		branchRepository.deleteAllByBank(bankById.get());
		return new ResponseEntity("Deleted Bank by ID: " + id + " with all branches", HttpStatus.OK);
	}

}
