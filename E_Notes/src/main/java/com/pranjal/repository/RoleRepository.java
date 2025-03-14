package com.pranjal.repository;

import com.pranjal.enitity.Role;
import com.pranjal.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
