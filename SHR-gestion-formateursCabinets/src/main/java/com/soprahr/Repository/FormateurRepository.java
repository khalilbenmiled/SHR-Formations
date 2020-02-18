package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.soprahr.models.Formateur;

public interface FormateurRepository extends JpaRepository<Formateur, Integer>{
	
	@Query(value = "SELECT * FROM Formateur f WHERE f.bu = :bu", nativeQuery = true)		
	public Formateur getFormateurByBU(@Param("bu") int bu);
	
	@Query(value = "SELECT * FROM Formateur f WHERE f.expertise = :expertise", nativeQuery = true)		
	public Formateur getFormateurByExpertise(@Param("expertise") int expertise);
	

}
