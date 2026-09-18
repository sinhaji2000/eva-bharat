package com.example.ava_bharat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ava_bharat.entity.Window;


@Repository 
public interface WindowRepository extends JpaRepository<Window , Long>{
    
}
