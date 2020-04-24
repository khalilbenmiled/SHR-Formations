package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.soprahr.model.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Integer>{

	@Query(value = "SELECT * FROM Projet p WHERE p.id_team_lead = :id ", nativeQuery = true)		
	public List<Projet> getProjetByTL(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Projet p WHERE p.id_manager = :id ", nativeQuery = true)		
	public List<Projet> getProjetByMG(@Param("id") int id);
}
