package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, Long> {

      @Transactional
      @Modifying
      @Query(value= "DELETE FROM follows WHERE followerid = :followerId AND followingid = :followingId", nativeQuery = true)
      int deleteByFolloweridAndFollowingid(Long followerId, Long followingId);

      @Query(value= "SELECT followingid FROM follows WHERE followerid = :followerId", nativeQuery = true)
      List<Long> findFollowingIdsByFollowerId(Long followerId);

      Optional<Follow> findAllByFolloweridAndFollowingid(Long followerId, Long followingId);

}
