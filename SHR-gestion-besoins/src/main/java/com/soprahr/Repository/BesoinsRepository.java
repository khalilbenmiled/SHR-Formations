package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.model.Besoins;


public interface BesoinsRepository extends JpaRepository<Besoins, Integer>{
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.id_user = :id AND b.planifier = 0 AND b.publier =0", nativeQuery = true)		
	public List<Besoins> getBesoinsByUser(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.id_user = :id AND b.planifier = 0 AND send_tosf = 0", nativeQuery = true)		
	public List<Besoins> getBesoinsByUserForManager(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.planifier = 0 AND b.publier = 0", nativeQuery = true)		
	public List<Besoins> findAllNotPublish();
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.id_user = :id AND b.nom = :nom ", nativeQuery = true)		
	public Besoins getBesoinsTLbyTheme(@Param("id") int id , @Param("nom") String nom);
	
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.nom = :nom  AND b.id_user = :id ", nativeQuery = true)		
	public Besoins getBesoinsByThemeNom(@Param("nom") String nom , @Param("id") int id );
	
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.nom = :nom  AND b.id_user = :id AND b.send_tosf = 0", nativeQuery = true)		
	public Besoins getBesoinsByThemeNomAndNotPublish(@Param("nom") String nom , @Param("id") int id );
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.type = :type ", nativeQuery = true)		
	public List<Besoins> getBesoinsByThemeType(@Param("type") String type);
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.planifier = 0", nativeQuery = true)		
	public List<Besoins> getBesoinNonPlanifier();
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.planifier = 1", nativeQuery = true)		
	public List<Besoins> getBesoinPlanifier();
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.planifier = 1 AND b.id_user = :id", nativeQuery = true)		
	public List<Besoins> getBesoinPlanifierByCollaborateur(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.planifier = 0 AND b.id_user = :id ", nativeQuery = true)		
	public List<Besoins> getBesoinNonPlanifierByCollaborateur(@Param("id") int id);
	
	@Query(value = "SELECT * FROM Besoins b WHERE b.id_user = :id", nativeQuery = true)		
	public List<Besoins> getAllBesoinsByCollaborateur(@Param("id") int id);

	
		
}
