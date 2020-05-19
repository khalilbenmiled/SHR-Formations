package com.soprahr.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.soprahr.model.BesoinsPublier;

public interface BesoinsPublierRepository extends JpaRepository<BesoinsPublier, Integer>{

	@Query(value = "SELECT * FROM besoins_publier b WHERE b.theme = :theme AND b.quarter = :quarter ", nativeQuery = true)		
	public BesoinsPublier getBesoinsPublierByThemeAndQuarter(@Param("theme") String theme , @Param("quarter") int quarter);
	
	@Query(value = "SELECT * FROM besoins_publier b WHERE b.theme = :theme AND b.quarter = :quarter AND b.id_manager = :id", nativeQuery = true)		
	public BesoinsPublier getBesoinsPublierByThemeAndQuarterAndManager(@Param("theme") String theme , @Param("quarter") int quarter , @Param("id") int id);
	
	@Query(value = "SELECT * FROM besoins_publier b WHERE b.theme = :theme AND b.quarter = :quarter AND b.publier = 1 ", nativeQuery = true)		
	public BesoinsPublier getBesoinsPublierByThemeAndQuarterTF(@Param("theme") String theme , @Param("quarter") int quarter);
	
	@Query(value = "SELECT * FROM besoins_publier b WHERE b.publier = 0 AND b.id_manager =:id", nativeQuery = true)		
	public List<BesoinsPublier> getAllNotPublish(@Param(value="id") int id);
	
	@Query(value = "SELECT * FROM besoins_publier b WHERE b.publier = 1 ", nativeQuery = true)		
	public List<BesoinsPublier> getAllPublish();

	@Query(value = "SELECT * FROM besoins_publier b WHERE b.theme = :theme AND b.quarter = :quarter AND b.publier = 1 AND b.id != :id", nativeQuery = true)		
	public BesoinsPublier checkIfExist(@Param("theme") String theme , @Param("quarter") int quarter , @Param("id") int id);
	
	
}
