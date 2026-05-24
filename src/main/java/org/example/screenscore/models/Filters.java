package org.example.screenscore.models;

public enum Filters {
    Alpha_Asc,
    Alpha_Desc,
    Rating_Asc,
    Rating_Desc;

    public static String[] getFilters() {
        return new  String[] {"A→Z", "Z→A", "Rating ↓", "Rating ↑"};
    }

    public static Filters getFilter(String filter) {
        return switch (filter) {
            case "A→Z" -> Alpha_Asc;
            case "Z→A" -> Alpha_Desc;
            case "Rating ↓" -> Rating_Desc;
            case "Rating ↑" -> Rating_Asc;
            default -> null;
        };
    }
}
