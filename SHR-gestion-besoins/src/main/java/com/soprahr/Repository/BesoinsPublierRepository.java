package com.soprahr.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.soprahr.model.BesoinsPublier;

public interface BesoinsPublierRepository extends JpaRepository<BesoinsPublier, Integer>{

	@Query(value = "SELECT * FROM besoins_publier b WHERE b.theme = :theme AND b.quarter = :quarter ", nativeQuery = true)		
	public BesoinsPublier getBesoinsPublierByThemeAndQuarter(@Param("theme") String id , @Param("quarter") int quarter);
	
	@Query(value = "SELECT * FROM besoins_publier b WHERE b.publier = 0 ", nativeQuery = true)		
	public List<BesoinsPublier> getAllNotPublish();
	
	
	
}
