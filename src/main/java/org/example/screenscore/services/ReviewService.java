package org.example.screenscore.services;

import org.example.screenscore.dao.ReviewDao;
import org.example.screenscore.models.ReviewClass;

import java.util.List;

public class ReviewService {
    private static final ReviewDao reviewDao = new ReviewDao();

    public ReviewClass addReview(ReviewClass review){
        return reviewDao.addReview(review);
    }

    public List<ReviewClass> getAllReviews(){
        List<ReviewClass> reviews = reviewDao.getAllReviews();

        return reviews;
    }

    public void deleteReview(int id){
        reviewDao.deleteReview(id);
    }

    public void updateReview(ReviewClass review){
        reviewDao.updateReview(review);
    }
}
