package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprahr.models.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {

}
