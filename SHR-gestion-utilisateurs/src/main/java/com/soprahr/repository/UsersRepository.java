package com.soprahr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM User u WHERE u.email = :email", nativeQuery = true)		
	public User getUserByEmail(@Param("email") String email);
	
	@Query(value = "SELECT * FROM User u WHERE u.role = 0", nativeQuery = true)		
	public List<User> getAllCollaborateur();
	
	@Query(value = "SELECT * FROM User u WHERE u.email = :email and u.password = :password", nativeQuery = true)		
	public User verifyPassword( @Param("email") String email , @Param("password") String password );

}
