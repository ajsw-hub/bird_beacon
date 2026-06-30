package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Follow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowRepository extends CrudRepository<Follow, Long> {

//      void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
//
      @Query(value= "SELECT followingid FROM follows WHERE followerid = :followerId", nativeQuery = true)
      List<Long> findFollowingIdsByFollowerId(Long followerId);


}
