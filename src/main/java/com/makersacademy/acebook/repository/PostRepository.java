package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    public List<Post> findByPosteridOrderByIdDesc(Long userid);

    @Query(
            value = "SELECT * from posts order by id DESC;",
            nativeQuery = true
    )
    List<Post> findAllOrderByIdDesc();


}
