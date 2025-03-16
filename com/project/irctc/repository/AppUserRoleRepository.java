package com.project.irctc.repository;

import com.project.irctc.model.appuserrole.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole,Long> {
    List<AppUserRole> findByUserId(Long userId);
}
