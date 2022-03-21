package com.pch777.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByEmail(String email);

	@Transactional
    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
	int enableUser(String email);
}
