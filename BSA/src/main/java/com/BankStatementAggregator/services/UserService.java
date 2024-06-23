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

import com.BankStatementAggregator.DTOs.UserDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.Bank;
import com.BankStatementAggregator.Enitiy.Company;
import com.BankStatementAggregator.Enitiy.User;
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

	public ResponseEntity<?> userRegistrationProcess(UserDTO userDTO) {

		if (!userDTO.getcPassword().equals(userDTO.getUserPassword())) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "Password and confirm password are different", null));
		}
		User userMap = mapper.map(userDTO, User.class);

		if (userDTO.getCompanyCode() != null) {
			Optional<Company> company = Optional
					.ofNullable(companyRepository.findByCompanyCode(userDTO.getCompanyCode()));
			if (company.isPresent()) {
				userMap.setCompany(company.get());
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
								"Company code is invalid!! Please Check Again.", null));
			}
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "Company code is empty!! Please Check Again.", null));
		}
		if (userDTO.getBankCodes() != null) {
			Set<Bank> banks = new HashSet<>();
			for (int bankCode : userDTO.getBankCodes()) {
				Optional<Bank> bank = Optional.ofNullable(bankRepository.findByBankCode(bankCode));
				if (bank.isPresent()) {
					banks.add(bank.get());
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
							HttpStatus.INTERNAL_SERVER_ERROR.value(), "One or More bank code's are invalid", null));
				}
			}
			userMap.setBanks(banks);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "Bank Code's is empty!! Please Check Again.", null));
		}

		userMap.setUserIsElgible(false);
		userMap.setUserRegistorDate(LocalDate.now());
		userMap.setUserElgibleDate(null);
		userMap.setUserInvalidAttempts(5);
		userMap.setUserLastLogin(null);
		userMap.setUserLastPasswordChangeDate(null);

		userRepository.save(userMap);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(HttpStatus.CREATED.value(), "User Registration Succesfull", null));

	}

	public ResponseEntity<?> userLoginVerfication(String username, String password) {
		User userInfo = userRepository.findByUserName(username);
		if (userInfo != null) {
			String userPassword = userInfo.getUserPassword();
			if (userPassword.equals(password)) {
				if (userInfo.isUserIsElgible()) {
					if (userInfo.getUserInvalidAttempts() == 0) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),
										"User account is blocked due to 5 invalid attempts", null));
					}

				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(
							HttpStatus.BAD_REQUEST.value(), "User is not Authorized to login. Contact Admin!", null));
				}
			} else {
				Integer UpdatedAttempts = 0;
				Integer userInvalidAttempts = userInfo.getUserInvalidAttempts();
				if (userInvalidAttempts >= 1) {
					UpdatedAttempts = userInvalidAttempts - 1;
					userRepository.updateUserInvalidAttemptsByUserName(UpdatedAttempts, username);
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),
									"User is blocked. Please contact to admin for unlock!", null));
				}
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
						.body(new ApiResponse(HttpStatus.NOT_ACCEPTABLE.value(),
								"Password is invalid. More" + UpdatedAttempts + " are left", UpdatedAttempts));
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ApiResponse(HttpStatus.NOT_FOUND.value(), "Username " + username + "is invalid", username));
		}
		userRepository.updateUserLastLogin(LocalDateTime.now(), username);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(HttpStatus.OK.value(), "User Logined Succesfully.", null));

	}

	public ResponseEntity<?> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserToUserDTO userDTO = mapper.map(user, UserToUserDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
		.body(new ApiResponse(HttpStatus.NO_CONTENT.value(),"No user found with user Id" + id,id));
	}

	public List<UserToUserDTO> getAllUsers() {
		List<User> allUsers = userRepository.findAll();

		return allUsers.stream().map(user -> mapper.map(user, UserToUserDTO.class)).collect(Collectors.toList());

	}

}
