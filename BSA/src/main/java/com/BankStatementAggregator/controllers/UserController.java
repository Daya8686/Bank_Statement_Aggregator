package com.BankStatementAggregator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BankStatementAggregator.DTOs.UserDTO;
import com.BankStatementAggregator.DTOs.UserToUserDTO;
import com.BankStatementAggregator.Enitiy.User;
import com.BankStatementAggregator.services.UserService;
import com.BankStatementAggregator.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
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
		try {
		List<UserToUserDTO> allUsers = userService.getAllUsers();
		if(allUsers.isEmpty()) {
			return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(HttpStatus.NO_CONTENT.value(),"No Users Found",null));
		}
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Unknown Exception",e.getLocalizedMessage()));
		}
	}

}
