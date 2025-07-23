package com.shopez.app.dao;

import com.shopez.app.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends CrudRepository<Role,String> {

    Optional<Role> findByRoleName(String user);
}
