//package com.bezkoder.spring.data.jpa.test.repository;
//
//import java.util.List;
//
//import com.bezkoder.spring.data.jpa.test.model.entity.App_Role;
//import com.bezkoder.spring.data.jpa.test.model.entity.UserRole;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//@Repository
//@Transactional
//public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
//    @Query(value = "Select ur.role_id from user_role ur where ur.user_id = :userId",nativeQuery = true)
//    public List<Integer> getRoleByIdUser(@Param("userId") Integer userId);
//
//    @Modifying
//    @Transactional
//    @Query(value = "insert into user_role(`id`,`user_id`,`role_id`) value(?1,?2,?3)",nativeQuery = true)
//    public void add(Integer id,Integer user_id,Integer roleId);
//}
