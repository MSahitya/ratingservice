package com.tastebytes.ratingservice.controller;

import com.tastebytes.ratingservice.entity.UserRating;
import com.tastebytes.ratingservice.service.UserRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/userRatings")
public class UserRatingController {
    @Autowired
    public UserRatingService userRatingService;

    @Operation(summary = "Create new User Rating", description = "")
    @PostMapping("/")
    public UserRating postUserRating(@RequestBody UserRating userRating){
        return userRatingService.saveUserRating(userRating);
    }
    @Operation(
            summary = "Get User Rating",
            description = "Get the rating of a specific user by their user ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found the user rating",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserRating.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = UserRating.class))
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "User rating not found")
            }
    )
    @GetMapping(value="/{userId}",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE} )
    public Optional<UserRating> getUserRating(@PathVariable @Valid String userId){
        Optional<UserRating> userRating = userRatingService.getUserRating(userId);
        if (userRating.isPresent()) {
            return userRating;
        } else {
            throw new ResourceNotFoundException("User rating not found for user ID: " + userId);
        }
    }

    @Operation(summary = "Get All user Ratings", description = "")
    @GetMapping("/getAllRatings")
    public Page<UserRating> getAllRatings(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy){
        return userRatingService.getAllUserRatings(page, size, sortBy);
    }

    @Operation(summary = "Save List of  User Rating", description = "")
    @PostMapping("/save")
    public List<UserRating> saveUserRatings(@RequestBody List<UserRating> userRatings) {
        return userRatingService.saveUserRatings(userRatings);
    }

    @Operation(summary = "Get high User Rating", description = "")
    @GetMapping("/highRatings")
    public List<UserRating> getHighRatings() {
        return userRatingService.getHighRatings();
    }

    @Operation(summary = "Get Average User Rating", description = "")
    @GetMapping("/averageRatings")
    public List<Document> getAverageRatings() {
        return userRatingService.getAverageRatings();
    }

    @Operation(summary = "Get Sorted  User Rating", description = "")
    @GetMapping("/sortedRatings")
    public List<UserRating> getSortedRatings() {
        return userRatingService.getSortedRatings();
    }
    @Operation(summary = "Get Limited User Rating", description = "")
    @GetMapping("/limitedRatings")
    public List<UserRating> getLimitedRatings() {
        return userRatingService.getLimitedRatings();
    }
    @Operation(summary = "Get User Rating with User Details", description = "")
    @GetMapping("/ratingsWithUserDetails")
    public List<Document> getRatingsWithUserDetails() {
        return userRatingService.getRatingsWithUserDetails();
    }


}
