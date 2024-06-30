package com.BankStatementAggregator.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.BankStatementAggregator.Enitiy.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUserEmail(String userEmail);
	public User findByUserName(String username);
	
	@Modifying
    @Transactional
    @Query("Update User u set u.userInvalidAttempts=:invalidAttempts where u.userName=:username")
	public int updateUserInvalidAttemptsByUserName(@Param("invalidAttempts") Integer userInvalidAttempts, @Param("username") String userName);
	
	@Modifying
    @Transactional
    @Query("update User u set u.userLastLogin=:userLastLogin where u.userName=:username")
	public int updateUserLastLogin(@Param("userLastLogin") LocalDateTime userLastLogin, @Param ("username") String userName);
	
}
