package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.Score;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
	
	@Query(value = "SELECT * FROM Score s WHERE s.id_collaborateur = :id", nativeQuery = true)		
	public List<Score> getScoreByCollaborateur(@Param("id") int id);
}
