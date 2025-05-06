package com.demo.maindata.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.maindata.entity.RoleEntity;
import com.demo.maindata.entity.RoleName;
import com.demo.maindata.entity.Role_Name;
import com.demo.maindata.entity.Role_Name.Role;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);
}