package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprahr.models.Session;

public interface SessionRepository extends JpaRepository<Session, Integer>{

}
