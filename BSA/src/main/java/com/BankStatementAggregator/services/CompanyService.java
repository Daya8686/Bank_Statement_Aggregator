package com.BankStatementAggregator.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.errorhandiling.CompanyServiceExceptionHandler;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.CompanyRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<?> creatingCompany(CompanyDTO companyDTO) {
		Company company = mapper.map(companyDTO, Company.class);
		Company saveCompany = companyRepository.save(company);
		if (saveCompany.equals(null)) {
			throw new CompanyServiceExceptionHandler("Unable to save or add company information",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(HttpStatus.CREATED.value(), "Company information added succesfully", companyDTO));

	}

	public ResponseEntity<?> getCompanyById(Long id) {
		Optional<Company> companyById = companyRepository.findById(id);
		if (companyById.isEmpty()) {
			throw new CompanyServiceExceptionHandler("Company with Id: " + id + " is not available",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(companyById.get(), HttpStatus.ACCEPTED);

	}

	public ResponseEntity<List<Company>> getAllCompanies() {
		List<Company> all = companyRepository.findAll();
		if (all == null) {
			throw new CompanyServiceExceptionHandler("No Company Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Company>>(all, HttpStatus.OK);
	}

	public ResponseEntity<?> updateCompany(Long id, CompanyDTO companyDTO) {
		Optional<Company> companyById = companyRepository.findById(id);
		if (companyById.isEmpty()) {
			throw new CompanyServiceExceptionHandler("Company with ID: " + id + " is not available for update.",
					HttpStatus.BAD_REQUEST);
		}
		Company existingCompanyWithSameCode = companyRepository.findByCompanyCode(companyDTO.getCompanyCode());
		if (existingCompanyWithSameCode != null && !existingCompanyWithSameCode.getCompanyId().equals(id)) {
			throw new CompanyServiceExceptionHandler("Company code " + companyDTO.getCompanyCode() + " already exists.",
					HttpStatus.BAD_REQUEST);
		}

		Company company = companyById.get();
		company.setCompanyCode(companyDTO.getCompanyCode());
		company.setCompanyName(companyDTO.getCompanyName());
//		if(!companyById.getBankCodes().equals(null)) {
//		
		Set<Integer> bankCodes = companyDTO.getBankCodes();
		Set<Bank> banks = new HashSet<>();
		for (Integer code : bankCodes) {
			Bank byBankCode = bankRepository.findByBankCode(code);
			banks.add(byBankCode);
		}
		company.setBanks(banks);

//	}

		Company updatedCompany = companyRepository.save(company);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Company Updated!", updatedCompany));
	}

	public ResponseEntity<?> getRemoveById(Long id) {
		Optional<Company> companyById = companyRepository.findById(id);
		if (companyById.isEmpty()) {
			throw new CompanyServiceExceptionHandler("Company with ID: " + id + " is not available for delete.",
					HttpStatus.BAD_REQUEST);
		}
		companyRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "Deleted Company by ID: " + id, null));

	}

}
