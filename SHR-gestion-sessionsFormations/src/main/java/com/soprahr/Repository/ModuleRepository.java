package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer>{

	@Query(value = "SELECT * FROM Module m WHERE m.nom = :nom AND m.description = :description", nativeQuery = true)		
	public Module getModuleByNomAndDescription(@Param("nom") String nom , @Param("description") String description);
}
