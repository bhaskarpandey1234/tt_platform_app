package com.project.irctc.repository;


import com.project.irctc.enums.RoleName;
import com.project.irctc.model.appuserrole.MasterRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<MasterRole, Long> {

    Optional<MasterRole> findByName(RoleName name);

    boolean existsByName(RoleName roleEnum);
}
