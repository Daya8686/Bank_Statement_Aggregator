package com.BankStatementAggregator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.UpdateUserDTO;
import com.BankStatementAggregator.DTOs.UserDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.User;
import com.BankStatementAggregator.errorhandiling.UserServiceException;
import com.BankStatementAggregator.services.UserService;
import com.BankStatementAggregator.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	public UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> userRegister(@Valid @RequestBody UserDTO userDTO) {

		return userService.userRegistrationProcess(userDTO);

	}

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestParam String username, @RequestParam String password) {
		return userService.userLoginVerfication(username, password);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		List<UserToUserDTO> allUsers = userService.getAllUsers();
		if(allUsers.isEmpty()) {
			throw new UserServiceException("No Users Found", HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO){
		
		return userService.updateUserById(id, updateUserDTO);
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		return userService.removeUserById(id);
	}

}
