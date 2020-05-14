package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.soprahr.model.TeamLead;

public interface TeamLeadRepository extends JpaRepository<TeamLead, Integer> {

	@Query(value = "SELECT * FROM team_lead t WHERE t.id_manager = :idm ", nativeQuery = true)		
	public List<TeamLead> getTeamLeadByManager(@Param("idm") int idm);
	
	@Query(value = "SELECT * FROM team_lead t WHERE t.id_team_lead = :id ", nativeQuery = true)		
	public TeamLead getTeamLeadById(@Param("id") int id);
	
	@Query(value = "SELECT * FROM team_lead t WHERE t.id_manager = 0 ", nativeQuery = true)		
	public List<TeamLead> getFreeTL();
}
