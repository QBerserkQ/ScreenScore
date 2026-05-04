package org.example.screenscore.dao;

import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    public ReviewClass addReview(ReviewClass rw){
        String sql = "INSERT INTO Reviews(rating, title, " +
                "description, type, image_url) VALUES(?,?,?,?,?)";

        try (var conn = DataBaseConnection.getConnection();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, rw.getRating());
            pstmt.setString(2, rw.getTitle());
            pstmt.setString(3, rw.getDescription());
            pstmt.setString(4, rw.getType().toString());
            pstmt.setString(5, rw.getImageUrl());
            int affectedRows = pstmt.executeUpdate();

            if(affectedRows == 0){
                throw new RuntimeException("INSERT не добавил ни одной строки (affectedRows=0)");
            }

            try(ResultSet key = pstmt.getGeneratedKeys()) {
                if(key.next()){
                    int generatedId = key.getInt(1);
                    rw.setId(generatedId);
                }else {
                    throw new RuntimeException("БД не вернула сгенерированный ключ (id)");
                }
            }

            return rw;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении Review", e);
        }
    }

    public List<ReviewClass> getAllReviews(){
        List<ReviewClass> reviews = new ArrayList<>();

        String sql = "SELECT id, rating, title, description, type, image_url FROM Reviews";

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

    public List<ReviewClass> getByType(Type type){
        String sql = "SELECT * FROM Reviews WHERE type = ?";
        List<ReviewClass> reviews = new ArrayList<>();

        try (var conn = DataBaseConnection.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            ReviewClass review;

            pstmt.setString(1, String.valueOf(type));
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String description = rs.getString("description");

                if(description==null){
                    description = "---";
                }

                Type type1 = Type.valueOf(rs.getString("type"));
                String imageUrl = rs.getString("image_url");
                if(imageUrl==null){
                    imageUrl = "photo";
                }

                review = new ReviewClass(id, rating, title, description, type1, imageUrl);

                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении из Review", e);
        }

        return reviews;
    }

    public void deleteReview(int id){
        String sql = "DELETE FROM Reviews WHERE id = ?";

        try (var conn = DataBaseConnection.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении Review", e);
        }
    }

    public void updateReview(ReviewClass rw){
        String sql = "UPDATE Reviews SET rating = ? , "
                + "title = ?, "
                + "description = ?, "
                + "type = ?, "
                + "image_url = ? "
                + "WHERE id = ?";

        try (var conn = DataBaseConnection.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rw.getRating());
            pstmt.setString(2, rw.getTitle());
            pstmt.setString(3, rw.getDescription());
            pstmt.setString(4, rw.getType().toString());
            pstmt.setString(5, rw.getImageUrl());
            pstmt.setInt(6, rw.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при изменении Review", e);
        }
    }
}
