package com.bezkoder.spring.data.jpa.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T>
    extends JpaRepository<T, Object>, JpaSpecificationExecutor<T> {

  @Query("select p from #{#entityName} p where p.id = ?1")
  T getById(int id);

  @Query("select p from #{#entityName} p where p.name = ?1")
  T getByName(String name);
}
