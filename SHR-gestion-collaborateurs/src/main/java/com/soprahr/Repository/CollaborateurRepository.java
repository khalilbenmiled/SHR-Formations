package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.Collaborateur;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer>{
	@Query(value = "SELECT * FROM Collaborateur c WHERE c.id_team_leader = :id", nativeQuery = true)		
	public List<Collaborateur> getCollaborateurByTL(@Param(value ="id") int id);
	
	@Query(value = "SELECT * FROM Collaborateur c WHERE c.id_collaborateur = :id", nativeQuery = true)		
	public Collaborateur getCollaborateurByIdCollaborateur(@Param(value ="id") int id);
	
}
