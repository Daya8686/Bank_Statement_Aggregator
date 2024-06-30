package com.BankStatementAggregator.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.BankStatementAggregator.DTOs.UpdateUserDTO;
import com.BankStatementAggregator.DTOs.UserDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.Enitiy.User;
import com.BankStatementAggregator.errorhandiling.UserServiceException;
import com.BankStatementAggregator.repository.BankRepository;
import com.BankStatementAggregator.repository.CompanyRepository;
import com.BankStatementAggregator.repository.UserRepository;
import com.BankStatementAggregator.util.ApiResponse;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private BankRepository bankRepository;

	/* User Registration method */
	
	public ResponseEntity<?> userRegistrationProcess(UserDTO userDTO) {

		if (!userDTO.getcPassword().equals(userDTO.getUserPassword())) {
			throw new UserServiceException("Password and confirm password are different", HttpStatus.BAD_REQUEST);

		}
		User userMap = mapper.map(userDTO, User.class);

		if (!processCompanycode(userDTO, userMap)) {
			throw new UserServiceException("Company code is invalid!! Please Check Again.", HttpStatus.BAD_REQUEST);
		}

//		if (!processBankCode(userDTO, userMap)) {
//			throw new UserServiceException("One or More bank code's are invalid", HttpStatus.BAD_REQUEST);
//		}

		defaultsInitialization(userMap);

		userRepository.save(userMap);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(HttpStatus.CREATED.value(), "User Registration Succesfull", null));

	}

	public boolean processCompanycode(UserDTO userDTO, User userMap) {

		if (userDTO.getCompanyCode() != null) {
			Optional<Company> company = Optional
					.ofNullable(companyRepository.findByCompanyCode(userDTO.getCompanyCode()));
			if (company.isPresent()) {
				userMap.setCompany(company.get());
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

//	public boolean processBankCode(UserDTO userDTO, User userMap) {
//		if (userDTO.getBankCodes() != null) {
//			Set<Bank> banks = new HashSet<>();
//			for (int bankCode : userDTO.getBankCodes()) {
//				Optional<Bank> bank = Optional.ofNullable(bankRepository.findByBankCode(bankCode));
//				if (bank.isPresent()) {
//					banks.add(bank.get());
//					userMap.setBanks(banks);
//					return true;
//				} else {
//					return false;
//				}
//			}
//			
//		}
//		return false;
//
//	}

	private void defaultsInitialization(User userMap) {
		userMap.setUserIsElgible(false);
		userMap.setUserRegistorDate(LocalDate.now());
		userMap.setUserElgibleDate(null);
		userMap.setUserInvalidAttempts(5);
		userMap.setUserLastLogin(null);
		userMap.setUserLastPasswordChangeDate(null);
	}

	
	/* Login method Starts here */
	
	public ResponseEntity<?> userLoginVerfication(String username, String password) {
		User userInfo = userRepository.findByUserName(username);
		if (userInfo != null) {
			String userPassword = userInfo.getUserPassword();
			if (userPassword.equals(password)) {
				if (userInfo.isUserIsElgible()) {
					if (userInfo.getUserInvalidAttempts() == 0) {
						throw new UserServiceException("User account is blocked due to 5 invalid attempts", HttpStatus.FORBIDDEN);
					}

				} else {
					throw new UserServiceException("User is not Authorized to login. Contact Admin!", HttpStatus.UNAUTHORIZED);
					
				}
			} else {
				Integer UpdatedAttempts = 0;
				Integer userInvalidAttempts = userInfo.getUserInvalidAttempts();
				if (userInvalidAttempts >= 1) {
					UpdatedAttempts = userInvalidAttempts - 1;
					userRepository.updateUserInvalidAttemptsByUserName(UpdatedAttempts, username);
				} else {
					throw new UserServiceException("User is blocked. Please contact to admin for unlock!", HttpStatus.FORBIDDEN);
				}
				throw new UserServiceException("Password is invalid. More" + UpdatedAttempts + " are left", HttpStatus.UNAUTHORIZED);
				
			}
		} else {
			throw new UserServiceException("Username " + username + "is invalid", HttpStatus.FORBIDDEN);
			
		}
		userRepository.updateUserLastLogin(LocalDateTime.now(), username);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "User Logined Succesfully.", null));

	}

	/* Get User By Id */
	public ResponseEntity<?> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserToUserDTO userDTO = mapper.map(user, UserToUserDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiResponse(HttpStatus.NO_CONTENT.value(), "No user found with user Id" + id, id));
	}

	/* Get All Users */
	public List<UserToUserDTO> getAllUsers() {
		List<User> allUsers = userRepository.findAll();

		return allUsers.stream().map(user -> mapper.map(user, UserToUserDTO.class)).collect(Collectors.toList());

	}

	public ResponseEntity<?> updateUserById(Long id, UpdateUserDTO updateUser) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserServiceException("User with ID: "+id+" is not available", HttpStatus.BAD_REQUEST);
		}
		User userInfo = user.get();
		userInfo.setUserName(updateUser.getUserName());
		userInfo.setUserEmail(updateUser.getUserEmail());
//		if(!updateUser.getBankCodes().equals(null)) {
//			
//		Set<Integer> bankCodes = updateUser.getBankCodes();
//		Set <Bank> banks=new HashSet<>();
//		for(Integer code: bankCodes) {
//			Bank byBankCode = bankRepository.findByBankCode(code);
//			banks.add(byBankCode);
//		}
//		userInfo.setBanks(banks);
//		}
		
		Company byCompanyCode = companyRepository.findByCompanyCode(updateUser.getCompanyCode());
		if(!byCompanyCode.equals(null)) {
			userInfo.setCompany(byCompanyCode);
		}
		 User savedUser = userRepository.save(userInfo);
		
		 return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(HttpStatus.OK.value(), "User Updated Successfully.", updateUser));
		
	}

	public ResponseEntity<?> removeUserById(Long id) {
		Optional<User> userById = userRepository.findById(id);
		if(userById.isEmpty()) {
			throw new UserServiceException("User with ID: "+id+" is not avaliable!", HttpStatus.BAD_REQUEST);
		}
		userRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "User Deleted Successfully.", null));
	}

}
