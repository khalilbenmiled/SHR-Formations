package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprahr.models.MyNotification;


public interface NotificationsRepository extends JpaRepository<MyNotification, Integer>{

	
	@Query(value = "SELECT * FROM my_notification n WHERE n.id_collaborateur = :id", nativeQuery = true)		
	public List<MyNotification> getMesNotifications(@Param(value ="id") int id);
}
