package org.example.screenscore.models;

public class ReviewClass {
    private int id;
    private int rating;
    private String title;
    private String description;
    private Type type;
    private String imageUrl;

    public ReviewClass() {}

    public  ReviewClass(int rating, String title, String description, Type type, String imageUrl) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public  ReviewClass(int id, int rating, String title, String description, Type type, String imageUrl) {
        this(rating, title, description, type, imageUrl);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
