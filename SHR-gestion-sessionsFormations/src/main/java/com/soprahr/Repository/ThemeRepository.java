package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.Theme;



public interface ThemeRepository extends JpaRepository<Theme, Integer> {

	@Query(value = "SELECT * FROM Theme t WHERE t.type = :type", nativeQuery = true)		
	public List<Theme> getThemeByType(@Param("type") String type);
	
}
