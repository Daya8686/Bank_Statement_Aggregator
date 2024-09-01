package com.BankStatementAggregator.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.DTOs.CompanyToCompanyDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.Enitiy.User;
import com.BankStatementAggregator.errorhandiling.CompanyServiceExceptionHandler;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.CompanyRepository;
import com.BankStatementAggregator.repository.UserRepository;
import com.BankStatementAggregator.util.ApiResponse;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<?> creatingCompany(CompanyDTO companyDTO) {
		Company company = mapper.map(companyDTO, Company.class);
		
		Set<Integer> bankCodes = companyDTO.getBankCodes();
		Set<Bank> Allbanks=new HashSet<>();
		
		
		for(Integer bankcode: bankCodes) {
			Bank byBankCode = bankRepository.findByBankCode(bankcode);
			if(byBankCode!=null) {
				byBankCode.getCompany().add(company);
			Allbanks.add(byBankCode);
			}
			company.setBanks(Allbanks);
			
			
		}
		
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
//		List<CompanyToCompanyDTO> allCompany = all.stream().map(companyDetails->mapper.map(companyDetails, CompanyToCompanyDTO.class)).collect(Collectors.toList());
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

	public List<UserToUserDTO> getUserWithCompanyId(Long id) {
		Optional<Company> company = companyRepository.findById(id);
		 List<User> users= userRepository.findByCompany(company.get());
		 List<UserToUserDTO> usersData = users.stream().map(user->mapper.map(user, UserToUserDTO.class)).collect(Collectors.toList());
		 return usersData;		
	}

	public Set<Bank> getBanksOfCompany(Long id) {
		Optional<Company> companyId = companyRepository.findById(id);
		Set<Bank> banks = companyId.get().getBanks();
		return banks;
	}

}
