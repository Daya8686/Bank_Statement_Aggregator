package com.BankStatementAggregator.services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.BankStatementRecevierDTO;
import com.BankStatementAggregator.DTOs.StatementGenerationDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.BankStatement;
import com.BankStatementAggregator.Enitiy.Branch;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.errorhandiling.BankStatementGenerationExceptionHandler;
import com.BankStatementAggregator.feginclient.StatementGenerationFeginClient;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.BankStatementRepository;
import com.BankStatementAggregator.repository.BranchRepository;
import com.BankStatementAggregator.repository.CompanyRepository;
import com.BankStatementAggregator.util.FileExistenceApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class BankStatementService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private StatementGenerationFeginClient feginClient;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
    private ObjectMapper objectMapper; //To map objects
	
	@Autowired
	private BankStatementRepository bankStatementRepository;

	@Transactional
	public ResponseEntity<?> statementGenerator(StatementGenerationDTO generationDTO) {
		// TODO Auto-generated method stub
		if (!generationDTO.getCompanyCode().equals(null)) {
			Company companyCode = companyRepository.findByCompanyCode(generationDTO.getCompanyCode());
			if (!generationDTO.getBankCode().equals(null)) {
				Bank byBankCode = bankRepository.findByBankCode(generationDTO.getBankCode());
				Set<Bank> companyBanks = companyCode.getBanks();
				Bank bankMatch = companyBanks.stream().filter(bank -> bank.getBankCode() == byBankCode.getBankCode())
						.findFirst().get();
				if (!bankMatch.equals(null)) {
					if (!generationDTO.getBranchId().equals(null)) {
						Set<Branch> branches = byBankCode.getBranches();
						Branch branch = branches.stream()
								.filter(branchs -> branchs.getBranchId() == generationDTO.getBranchId()).findFirst()
								.get();
						if (!branch.equals(null)) {

							/////
							ResponseEntity<?> bankStatementsGenerator = feginClient
									.BankStatementsGenerator(generationDTO);
							
							 if (bankStatementsGenerator.getStatusCode().value() == 201) {
								
						            Object responseBody = bankStatementsGenerator.getBody();

						            if (responseBody instanceof LinkedHashMap) {
						                // Convert LinkedHashMap to FileExistenceApiResponse
						                FileExistenceApiResponse fileExistence = objectMapper.convertValue(responseBody, FileExistenceApiResponse.class);

						                if (fileExistence.isStatementExistance()) {
						                    return bankStatementsGenerator;
						                }
						                else {
						    BankStatementRecevierDTO statementData = objectMapper.convertValue(responseBody, BankStatementRecevierDTO.class);
							
							BankStatement bankStatement = new BankStatement();

//							BankStatementRecevierDTO statementData = (BankStatementRecevierDTO) bankStatementsGenerator.getBody();
							bankStatement.setStatementDate(statementData.getStatementDate());
							bankStatement.setStatementCode(statementData.getStatementCode());
							bankStatement
									.setCompany(companyRepository.findByCompanyCode(statementData.getCompanyCode()));
							bankStatement.setBank(bankRepository.findByBankCode(statementData.getBankCode()));
							Optional<Branch> branchById = branchRepository.findById(statementData.getBranchId());
							bankStatement.setBranch(branchById.get());
							bankStatementRepository.save(bankStatement);

							return bankStatementsGenerator;
						}
						                
					} else if (responseBody instanceof FileExistenceApiResponse) {
						
						                FileExistenceApiResponse fileExistence = (FileExistenceApiResponse) responseBody;
						                if (fileExistence.isStatementExistance()) {
						                    return bankStatementsGenerator;
						                } else {
						                    // Handle the case where the statement does not exist
						                	throw new BankStatementGenerationExceptionHandler("The statement does not exist or Error in parsing the statement",
													HttpStatus.INTERNAL_SERVER_ERROR);
						                }
						            }
			} else {
						            // Handle other status codes
						        	throw new BankStatementGenerationExceptionHandler("Error in parsing the statement",
											HttpStatus.INTERNAL_SERVER_ERROR);
						        }
							 throw new BankStatementGenerationExceptionHandler("Unable to parse and return the format",
										HttpStatus.INTERNAL_SERVER_ERROR);
//						        return bankStatementsGenerator;                
						                
						                
						                
						                
						                
							/////

						} else {
							throw new BankStatementGenerationExceptionHandler("Branch ID unable to match with Bank",
									HttpStatus.BAD_REQUEST);
						}
					} else {
						throw new BankStatementGenerationExceptionHandler("Branch ID is Empty", HttpStatus.BAD_REQUEST);
					}
				} else {
					throw new BankStatementGenerationExceptionHandler("Bank Code unable to match with Company",
							HttpStatus.BAD_REQUEST);
				}

			} else {
				throw new BankStatementGenerationExceptionHandler("Bank code is Empty", HttpStatus.BAD_REQUEST);
			}

		} else {
			throw new BankStatementGenerationExceptionHandler("Company code is Empty", HttpStatus.BAD_REQUEST);
		}

	}
	
	//_________________________________________________________________________________________
	//_________________________________________________________________________________________
	@Transactional
	public ResponseEntity<List<?>> statementGenerateByCompany(Integer companyCode) {
		
		Company companyDetails = companyRepository.findByCompanyCode(companyCode);
		System.out.println(companyDetails);
		if(companyDetails==null) {
			throw new BankStatementGenerationExceptionHandler("Company Code is invalid", HttpStatus.BAD_REQUEST);
		}
		StatementGenerationDTO generationDTO = new StatementGenerationDTO();
		
//		StatementGenarateByCompany statementGenarateByCompany=new StatementGenarateByCompany();
//		statementGenarateByCompany.setCompanyCode(companyCode);
//		
//		//THERE SETTING DEFAULT VALUES
//		statementGenarateByCompany.setDeleteAfterUpload(false);
//		statementGenarateByCompany.setCompanyName(companyDetails.getCompanyName());
//		statementGenarateByCompany.setNumberOfTransactionsGenerate(10);
//		
//		
//		//FROM HERE WE ARE GETTING BANKCODES NUMBERS
//		List<Integer> listBankCodes = new ArrayList<>();
//
//		// Ensure companyDetails and its methods are not null
//		if (companyDetails != null && companyDetails.getBanks() != null) {
//		    for (Bank bankValues : companyDetails.getBanks()) {
//		        if (bankValues != null) {
//		            listBankCodes.add(bankValues.getBankCode());
//		        }
//		    }
//		}
		
		List<Object> allTransactions=new LinkedList<>();
		generationDTO.setNumberOfTransactionsGenerate(10);
		generationDTO.setCompanyCode(companyDetails.getCompanyCode());
		generationDTO.setCompanyName(companyDetails.getCompanyName());
		generationDTO.setDeleteAfterUpload(false);
		Set<Bank> banks = companyDetails.getBanks();
		if(banks==null) {
			throw new BankStatementGenerationExceptionHandler("No Bank found for Company Code: "+companyCode, HttpStatus.BAD_REQUEST);
		}
		for(Bank bank:banks) {
			generationDTO.setBankCode(bank.getBankCode());
			Set<Branch> branches = bank.getBranches();
			if(branches==null) {
				throw new BankStatementGenerationExceptionHandler("No Branch found for Bank of Company Code: "+companyCode, HttpStatus.BAD_REQUEST);
			}
			for(Branch branche: branches) {
				generationDTO.setBranchId(branche.getBranchId());
				 Object body = statementGenerator(generationDTO).getBody();
				 allTransactions.add(body);
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(allTransactions);
		
		
		

//		statementGenarateByCompany.setBankCode(listBankCodes);
//
//		System.out.println("List of Bank Codes: " + listBankCodes);
//
//		
//		//FROM HERE WE ARE GETTING BRANCHES ID's
//		Set<Bank> banks = companyDetails.getBanks();
////		System.out.println("Banks Set: " + banks);
//
//		List<List<Long>> listBranchesOfBank=new LinkedList<>();
//		for (Bank bankForBranchs : banks) {
//			Set<Branch> branches = new HashSet<>();
//		    if (bankForBranchs != null && bankForBranchs.getBranches() != null) {
//		        branches.addAll(bankForBranchs.getBranches());
//		        System.out.println("Branches for Bank " + bankForBranchs.getBankCode() + ": " + bankForBranchs.getBranches());
//		    }
//		    List<Long> branchesOfBank=new LinkedList<>();
//		    for(Branch singleBranch:branches) {
//		    	branchesOfBank.add(singleBranch.getBranchId());
//		    }
//		    System.out.println(branchesOfBank);
//		    listBranchesOfBank.add(branchesOfBank);
//		    
//		    branchesOfBank=null;
//		    branches=null;
//		    
//		}
//		System.out.println(listBranchesOfBank);
//		statementGenarateByCompany.setBranchId(listBranchesOfBank);
//		
//		System.out.println(branches);
//		List<Long> listBranchCodes=new ArrayList<>();
//		for(Branch branchValues: branches) {
//			listBankCodes.add(branchValues.getBranchId());
//		}
//		
		
//		return null;
	}
	

}
