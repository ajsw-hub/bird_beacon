package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowsRepository extends CrudRepository<Post, Long> {

//    public List<Post> findByPosteridOrderByIdDesc(Long userid);




}
