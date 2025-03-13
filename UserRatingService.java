package com.tastebytes.ratingservice.service;

import com.tastebytes.ratingservice.entity.UserRating;
import com.tastebytes.ratingservice.repository.UserRatingRepository;
import org.apache.catalina.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Service
public class UserRatingService {
      @Autowired
      UserRatingRepository userRatingRepository;

      @Autowired
     MongoTemplate mongoTemplate;

    public UserRating saveUserRating(UserRating userRating){
       return userRatingRepository.save(userRating);
    }

    public Page<UserRating> getAllUserRatings(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRatingRepository.findAll(pageable);
    }
    public List<UserRating> getAllUserRatings() {
        return userRatingRepository.findAll();
    }
    public Optional<UserRating> getUserRating(String userId){

        return userRatingRepository.findByUserId(userId);
    }
    public List<UserRating> saveUserRatings(List<UserRating> userRatings) {
        return userRatingRepository.saveAll(userRatings);
    }
    public List<UserRating> getHighRatings(){
         Aggregation aggregation=newAggregation(match(Criteria.where("rating").gte(4)));
         AggregationResults<UserRating> results=mongoTemplate.aggregate(aggregation,"user_rating",UserRating.class);

        return results.getMappedResults();
    }
    // Group by productId and calculate average rating
    public List<Document> getAverageRatings() {
        Aggregation aggregation = newAggregation(
                group("productId").avg("rating").as("averageRating")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "user_rating", Document.class);
        return results.getMappedResults();
    }

    // Sort ratings in descending order
    public List<UserRating> getSortedRatings() {
        Aggregation aggregation = newAggregation(
                sort(Sort.by(Sort.Direction.DESC, "rating"))
        );
        AggregationResults<UserRating> results = mongoTemplate.aggregate(aggregation, "user_rating", UserRating.class);
        return results.getMappedResults();
    }

    // Limit the number of results to 10
    public List<UserRating> getLimitedRatings() {
        Aggregation aggregation = newAggregation(
                limit(10)
        );
        AggregationResults<UserRating> results = mongoTemplate.aggregate(aggregation, "user_rating", UserRating.class);
        return results.getMappedResults();
    }

    // Join ratings with user details
    public List<Document> getRatingsWithUserDetails() {
        Aggregation aggregation = newAggregation(
                lookup("users", "_id", "_id", "userDetails")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "user_rating", Document.class);
        return results.getMappedResults();
    }
}
