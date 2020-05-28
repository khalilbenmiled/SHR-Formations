package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.soprahr.models.Docs;


public interface DocsRepository extends JpaRepository<Docs, Integer>{

	@Query(value = "SELECT id , nom  , description  , doc_name ,doc_type  FROM Docs ", nativeQuery = true)		
	public List<Object> getAllDocs();
}
