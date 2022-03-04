package com.pch777.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pch777.model.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}

