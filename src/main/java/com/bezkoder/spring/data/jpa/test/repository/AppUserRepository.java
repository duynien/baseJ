package com.bezkoder.spring.data.jpa.test.repository;

import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import com.bezkoder.spring.data.jpa.test.model.entity.App_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AppUserRepository extends JpaRepository<App_User, Integer> {

  @Query(value ="Select e.* from App_User e Where e.USER_NAME = :userName",nativeQuery = true)
  public App_User findUserAccount(@Param(value = "userName") String userName);

  @Query(value ="Select e.* from App_User e Where e.email = :email",nativeQuery = true)
  public App_User findUserByEmail(@Param(value = "email") String email);

  @Query(value = "select max(user_id) from app_user",nativeQuery = true)
  public int getMaxId();

  @Query(value = "UPDATE App_User set Password = :password where e.USER_NAME = :userName",nativeQuery = true)
  public void changePassword(@Param(value = "password") String password,
                             @Param(value = "userName") String userName);

  @Query(value ="Select u.* from app_user u join user_role r on u.user_id = r.user_id " +
          "where r.ROLE_ID = 3",nativeQuery = true)
  public List<App_User> getListEmployee();

  @Query(value ="Select u.* from app_user u join user_role r on u.user_id = r.user_id " +
          "where r.ROLE_ID = 1",nativeQuery = true)
  public List<App_User> getListUser();
}
