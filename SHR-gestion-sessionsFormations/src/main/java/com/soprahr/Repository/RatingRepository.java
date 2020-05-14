package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprahr.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer>{

}
