package com.tastebytes.ratingservice.repository;

import com.tastebytes.ratingservice.entity.UserRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRatingRepository extends MongoRepository<UserRating,String> {
    Optional<UserRating>  findByUserId(String userId);
}
