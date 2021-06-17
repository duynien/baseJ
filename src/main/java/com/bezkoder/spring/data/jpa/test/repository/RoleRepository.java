package com.bezkoder.spring.data.jpa.test.repository;

import com.bezkoder.spring.data.jpa.test.model.entity.App_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<App_Role,Long> {
    @Query(value = "Select ur.role_name from App_Role ur where ur.role_id = :roleId ",nativeQuery = true)
    public String getRoleNames(@Param("roleId") Long roleId);
}
