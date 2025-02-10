package com.safety.repository;

import com.safety.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReopsitory extends JpaRepository<User, Integer> {
}
