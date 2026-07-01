package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Bird;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BirdRepository extends CrudRepository<Bird, Long> {

    @Query(
            value = "SELECT * FROM birds ORDER BY name",
            nativeQuery = true
    )
    List<Bird> findAllOrderByName();


    @Query(
            value = "SELECT COUNT(id) from birds;",
            nativeQuery = true
    )
    Integer countBirds();

    Optional<Bird> findByName(String name);
}
