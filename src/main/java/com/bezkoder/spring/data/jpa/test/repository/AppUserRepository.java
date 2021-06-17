package com.bezkoder.spring.data.jpa.test.repository;

import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AppUserRepository extends JpaRepository<App_User, Long> {

  @Query(value ="Select e.* from App_User e Where e.USER_NAME = :userName",nativeQuery = true)
  public App_User findUserAccount(@Param(value = "userName") String userName);

  @Query(value = "select max(user_id) from app_user",nativeQuery = true)
  public int getMaxId();
}
