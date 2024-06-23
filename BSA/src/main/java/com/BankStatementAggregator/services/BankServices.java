package com.BankStatementAggregator.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.BankDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class BankServices {

	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private ModelMapper mapper;
	
	

	public ResponseEntity<?> bankCreation(BankDTO bankDTO) {
		try {
		Bank bank = mapper.map(bankDTO, Bank.class);
		if(bankRepository.findByBankCode(bank.getBankCode())!=null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),"Bank is already avaliable with that bank code",bankDTO));
		}
		Bank saveBank = bankRepository.save(bank);
		if (saveBank==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Unable to create Bank due to unknown error",null));
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),"Bank Creation Success",saveBank));
		}
		}
		catch(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Bank creation failed"+ e.getMessage(),null));
		}
	}

}
