package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.makersacademy.acebook.controller.DTOs.PostView;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    public List<Post> findByPosterId(Long userid);
    @Query("""
        SELECT new com.makersacademy.acebook.controller.DTOs.PostView(
            p.id,
            p.content,
            p.posterId,
            p.latitude,
            p.longitude,
            p.birdId,
            p.user_img,
            b.name,
            b.images,
            p.dateOfSighting,
            p.createdAt,
            p.enabled,
            p.restricted
        )
        FROM Post p
        LEFT JOIN Bird b ON p.birdId = b.id
        ORDER BY p.id DESC
    """)
    List<PostView> postsJoinBird();
}
