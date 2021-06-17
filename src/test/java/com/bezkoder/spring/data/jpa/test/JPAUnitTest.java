package com.bezkoder.spring.data.jpa.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.repository.TypeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("/test.properties")
public class JPAUnitTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  TypeRepository repository;

  @Test
  public void should_find_no_tutorials_if_repository_is_empty() {
    Iterable<Type> tutorials = repository.findAll();

    assertThat(tutorials).isEmpty();
  }

  @Test
  public void should_store_a_tutorial() {
    Type tutorial = repository.save(new Type(1, "Tut desc", "adsadas"));

    assertThat(tutorial).hasFieldOrPropertyWithValue("name", "Tut title");
    assertThat(tutorial).hasFieldOrPropertyWithValue("id", "1 ");

  }
//
//  @Test
//  public void should_find_all_tutorials() {
//    Type tut1 = new Type("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type tut3 = new Type("Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    Iterable<Type> tutorials = repository.findAll();
//
//    assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
//  }
//
//  @Test
//  public void should_find_tutorial_by_id() {
//    Type tut1 = new Type("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type foundTutorial = repository.findById(tut2.getId()).get();
//
//    assertThat(foundTutorial).isEqualTo(tut2);
//  }
//
//  @Test
//  public void should_find_published_tutorials() {
//    Type tut1 = new Type("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type tut3 = new Type("Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    Iterable<Type> tutorials = repository.findByPublished(true);
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_find_tutorials_by_title_containing_string() {
//    Type tut1 = new Type("Spring Boot Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Java Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type tut3 = new Type("Spring Data JPA Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    Iterable<Type> tutorials = repository.findByTitleContaining("ring");
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_update_tutorial_by_id() {
//    Type tut1 = new Type("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type updatedTut = new Type("updated Tut#2", "updated Desc#2", true);
//
//    Type tut = repository.findById(tut2.getId()).get();
//    tut.setTitle(updatedTut.getTitle());
//    tut.setDescription(updatedTut.getDescription());
//    tut.setPublished(updatedTut.isPublished());
//    repository.save(tut);
//
//    Type checkTut = repository.findById(tut2.getId()).get();
//
//    assertThat(checkTut.getId()).isEqualTo(tut2.getId());
//    assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
//    assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
//    assertThat(checkTut.isPublished()).isEqualTo(updatedTut.isPublished());
//  }
//
//  @Test
//  public void should_delete_tutorial_by_id() {
//    Type tut1 = new Type("Tut#1", "Desc#1", true);
//    entityManager.persist(tut1);
//
//    Type tut2 = new Type("Tut#2", "Desc#2", false);
//    entityManager.persist(tut2);
//
//    Type tut3 = new Type("Tut#3", "Desc#3", true);
//    entityManager.persist(tut3);
//
//    repository.deleteById(tut2.getId());
//
//    Iterable<Type> tutorials = repository.findAll();
//
//    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
//  }
//
//  @Test
//  public void should_delete_all_tutorials() {
//    entityManager.persist(new Type("Tut#1", "Desc#1", true));
//    entityManager.persist(new Type("Tut#2", "Desc#2", false));
//
//    repository.deleteAll();
//
//    assertThat(repository.findAll()).isEmpty();
//  }
}
