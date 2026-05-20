package org.example.screenscore.services;

import org.example.screenscore.dao.ReviewDao;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.util.List;

public class ReviewService {
    private static final ReviewDao reviewDao = new ReviewDao();
    private static final ImageService imageService = new ImageService();

    public ReviewClass addReview(ReviewClass review){
        return reviewDao.addReview(review);
    }

    public List<ReviewClass> getAllReviews(){
        List<ReviewClass> reviews = reviewDao.getAllReviews();

        return reviews;
    }

    public void deleteReview(int id){
        String imageUrl = reviewDao.deleteReview(id);
        int count = reviewDao.countByImageUrl(imageUrl);
        if(count == 0){
            imageService.deleteImages(imageUrl);
        }
    }

    public void updateReview(ReviewClass review){
        reviewDao.updateReview(review);
    }

    public List<ReviewClass> getReviewsByType(Type type){
        return reviewDao.getByType(type);
    }
}
