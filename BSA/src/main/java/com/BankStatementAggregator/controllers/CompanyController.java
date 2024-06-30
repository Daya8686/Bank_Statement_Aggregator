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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.CompanyDTO;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.services.CompanyService;

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
		return companyService.getAllCompanies();
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
