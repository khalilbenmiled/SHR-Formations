package com.soprahr.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soprahr.models.Quiz;


public interface QuizRepository extends JpaRepository<Quiz, Integer>{

	@Query(value = "SELECT * FROM Quiz q WHERE q.deleted = false", nativeQuery = true)		
	public List<Quiz> findAllQuiz();
}
