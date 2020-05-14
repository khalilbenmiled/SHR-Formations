package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.soprahr.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{

}
