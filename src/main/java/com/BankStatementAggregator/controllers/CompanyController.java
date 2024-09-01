package com.BankStatementAggregator.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.DTOs.CompanyToCompanyDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.services.CompanyService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> AddCompany(@Valid @RequestBody CompanyDTO companyDTO){
		return companyService.creatingCompany(companyDTO);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable Long id){
		return companyService.getCompanyById(id);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Company>> getAllCompanies(){
		
	        return  companyService.getAllCompanies();
	}
	@GetMapping("/{id}/users")
	public ResponseEntity<?> getUserByCompanyId(@PathVariable Long id){
	List<UserToUserDTO> userWithCompanyId = companyService.getUserWithCompanyId(id);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userWithCompanyId);

		SimpleBeanPropertyFilter beanPropertyFilter= SimpleBeanPropertyFilter.filterOutAllExcept("userId","userName","userEmail");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UsersWithCompanyId", beanPropertyFilter);
		mappingJacksonValue.setFilters(filterProvider);
		return new ResponseEntity( mappingJacksonValue,HttpStatus.OK);
	}
	
	@GetMapping("/{id}/banks")
	public ResponseEntity<MappingJacksonValue> getBanksWithCompanyId(@PathVariable Long id){
		Set<Bank> banksOfCompany = companyService.getBanksOfCompany(id);
		MappingJacksonValue mappingJacksonValue =new MappingJacksonValue(banksOfCompany);
		SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("bankId","bankCode");
		FilterProvider filters=new SimpleFilterProvider().addFilter("BankWithOutBranch", filter);
		mappingJacksonValue.setFilters(filters);
		
		return new ResponseEntity(mappingJacksonValue,HttpStatus.OK);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCompanyById(@PathVariable Long id, @RequestBody CompanyDTO companyDTO){
		return companyService.updateCompany(id, companyDTO);
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> deleteCompanyById(@PathVariable Long id){
		
		return companyService.getRemoveById(id);
	}

	

}
