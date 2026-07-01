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
            u.username,
            u.profilepicture,
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
        JOIN User u ON p.posterId = u.id
        LEFT JOIN Bird b ON p.birdId = b.id
        WHERE p.enabled = TRUE
        AND p.restricted = FALSE
        ORDER BY p.id DESC
    """)
    List<PostView> postsJoinBird();
}
