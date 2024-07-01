package com.BankStatementAggregator.services;

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
	private BankStatementRepository bankStatementRepository;

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
							ResponseEntity<BankStatementRecevierDTO> bankStatementsGenerator = feginClient
									.BankStatementsGenerator(generationDTO);
							BankStatement bankStatement = new BankStatement();

							BankStatementRecevierDTO statementData = bankStatementsGenerator.getBody();
							bankStatement.setStatementDate(statementData.getStatementDate());
							bankStatement.setStatementCode(statementData.getStatementCode());
							bankStatement
									.setCompany(companyRepository.findByCompanyCode(statementData.getCompanyCode()));
							bankStatement.setBank(bankRepository.findByBankCode(statementData.getBankCode()));
							Optional<Branch> branchById = branchRepository.findById(statementData.getBranchId());
							bankStatement.setBranch(branchById.get());
							bankStatementRepository.save(bankStatement);

							return bankStatementsGenerator;
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
	

}
