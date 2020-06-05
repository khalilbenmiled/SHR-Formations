package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.Parcour;

public interface ParcourRepository extends JpaRepository<Parcour, Integer>{

	@Query(value = "SELECT * FROM Parcour p WHERE p.collaborateur_id = :id", nativeQuery = true)		
	public Parcour getParcourByCollaborateur(@Param(value ="id") int id);

}
