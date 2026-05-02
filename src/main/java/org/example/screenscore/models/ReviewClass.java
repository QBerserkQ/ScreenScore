package org.example.screenscore.models;

public class ReviewClass {
    private int id;
    private int rating;
    private String title;
    private String description;
    private Type type;
    private String imageUrl;

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
}
