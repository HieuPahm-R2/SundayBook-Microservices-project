package com.hieupham.service;


import com.hieupham.modal.Review;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.UserDTO;
import com.hieupham.payload.request.CreateReviewRequest;


import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req, UserDTO user, SalonDTO salon);

    List<Review> getReviewsBySalonId(Long productId);

    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception, AuthenticationException;

    void deleteReview(Long reviewId, Long userId) throws Exception, AuthenticationException;

}
