package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprahr.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
