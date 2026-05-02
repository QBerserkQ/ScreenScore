package org.example.screenscore.dao;

import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private static DataBaseConnection dbConnection = new DataBaseConnection();

    public void addReview(ReviewClass rw){
        String sql = "INSERT INTO ReviewDb(rating, title, " +
                "description, type, image_url) VALUES(?,?,?,?,?)";

        try (var conn = DataBaseConnection.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rw.getRating());
            pstmt.setString(2, rw.getTitle());
            pstmt.setString(3, rw.getDescription());
            pstmt.setString(4, rw.getType().toString());
            pstmt.setString(5, rw.getImageUrl());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<ReviewClass> getAllReviews(){
        List<ReviewClass> reviews = new ArrayList<>();

        var sql = "SELECT id, rating, title, description, type, image_url FROM ReviewDb";

        try (var conn = DataBaseConnection.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            ReviewClass review;

            while (rs.next()) {
                int id = rs.getInt("id");
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String description = rs.getString("description");

                if(description==null){
                    description = "---";
                }

                Type type = Type.valueOf(rs.getString("type"));
                String imageUrl = rs.getString("image_url");
                if(imageUrl==null){
                    imageUrl = "photo";
                }

                review = new ReviewClass(id, rating, title, description, type, imageUrl);

                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return reviews;
    }
}
