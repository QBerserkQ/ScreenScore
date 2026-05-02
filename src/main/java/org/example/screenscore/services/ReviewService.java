package org.example.screenscore.services;

import org.example.screenscore.dao.ReviewDao;
import org.example.screenscore.models.ReviewClass;

public class ReviewService {
    private static final ReviewDao reviewDao = new ReviewDao();

    public void addReview(ReviewClass review){
        reviewDao.addReview(review);
    }


}
