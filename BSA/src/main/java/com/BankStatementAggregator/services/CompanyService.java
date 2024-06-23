package com.BankStatementAggregator.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.repository.CompanyRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<?> creatingCompany(CompanyDTO companyDTO) {
		Company company = mapper.map(companyDTO, Company.class);
		Company saveCompany = companyRepository.save(company);
		if (saveCompany.equals(null)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unable to save or add company information", null));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(HttpStatus.CREATED.value(), "Company information added succesfully", companyDTO));

	}

}
